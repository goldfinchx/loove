package net.loove.chats.service;

import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.loove.chats.config.WebSocketProperties;
import net.loove.chats.dto.ChatDTO;
import net.loove.chats.dto.MessageDTO;
import net.loove.chats.model.Chat;
import net.loove.chats.model.Message;
import net.loove.chats.repository.ChatsRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "chats")
@RequiredArgsConstructor
public class ChatsService {

    private final ChatsRepository repository;
    private final WebSocketProperties properties;
    private final SimpMessagingTemplate messagingTemplate;

    public void handleMessage(MessageDTO messageDto) {
        final Chat chat = this.repository.findByUsers(messageDto.getSender(), messageDto.getReceiver()).orElseThrow();
        final Message msg = Message.builder()
            .chat(chat)
            .sender(messageDto.getSender())
            .content(messageDto.getContent())
            .at(Instant.ofEpochMilli(messageDto.getAt()))
            .build();

        chat.getMessages().add(msg);
        this.repository.save(chat);

        final String receiver = String.valueOf(messageDto.getReceiver());
        this.messagingTemplate.convertAndSendToUser(receiver, this.properties.getMessagesEndpoint(), messageDto);
    }

    @CachePut(key = "#chat.id")
    public List<ChatDTO> getUserChats(Long userId) {
        return this.repository.findByUser(userId)
            .stream()
            .map(this::serializeChat)
            .toList();
    }

    public ChatDTO serializeChat(Chat chat) {
        return ChatDTO.builder()
                .id(chat.getId())
                .users(chat.getUsers().toArray(new Long[0]))
                .messages(chat.getMessages()
                    .stream()
                    .map(this::serializeMessage)
                    .toArray(MessageDTO[]::new))
                .build();
    }

    @CachePut(key = "#chatId")
    public List<MessageDTO> getChatMessages(Long chatId) {
        final Chat chat = this.repository.findById(chatId).orElseThrow();
        return chat.getMessages()
            .stream()
            .map(this::serializeMessage)
            .toList();
    }

    public void saveChat(Chat chat) {
        this.repository.save(chat);
    }

    private Message deserializeMessage(MessageDTO messageDto) {
        final Chat chat = this.repository.findByUsers(messageDto.getSender(), messageDto.getReceiver()).orElseThrow();

        return Message.builder()
            .chat(chat)
            .sender(messageDto.getSender())
            .content(messageDto.getContent())
            .at(Instant.ofEpochMilli(messageDto.getAt()))
            .build();
    }

    private MessageDTO serializeMessage(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .receiver(message.getReceiver())
                .sender(message.getSender())
                .content(message.getContent())
                .at(message.getAt().toEpochMilli())
                .build();
    }

}
