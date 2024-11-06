package net.loove.chats.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.loove.chats.configs.properties.WebSocketProperties;
import net.loove.chats.dto.ChatDTO;
import net.loove.chats.dto.MessageDTO;
import net.loove.chats.model.Chat;
import net.loove.chats.model.Message;
import net.loove.chats.repository.ChatsRepository;
import net.loove.chats.utils.ChatSerializer;
import net.loove.chats.utils.MessageSerializer;
import org.springframework.cache.annotation.CachePut;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatsService {

    private final ChatsRepository repository;
    private final WebSocketProperties properties;
    private final SimpMessagingTemplate messagingTemplate;
    private final EventsService eventsService;
    private final ChatSerializer chatSerializer;
    private final MessageSerializer messageSerializer;

    public void handleMessage(MessageDTO messageDto) {
        final Chat chat = this.repository.findByUsers(messageDto.getReceiver(), messageDto.getSender()).orElseThrow();
        final Message msg = this.messageSerializer.deserialize(messageDto, chat);
        this.saveMessage(msg);
        this.sendMessage(messageDto);

        this.eventsService.publishChatMessageEvent(msg);
    }

    private void sendMessage(MessageDTO messageDto) {
        final String receiver = String.valueOf(messageDto.getReceiver());
        this.messagingTemplate.convertAndSendToUser(receiver, this.properties.getMessagesEndpoint(), messageDto);
    }

    public void saveMessage(Message message) {
        final Chat chat = message.getChat();
        chat.getMessages().add(message);
        this.saveChat(chat);
    }

    public void saveChat(Chat chat) {
        this.repository.save(chat);
    }

    @CachePut(cacheNames = "user-chats", key = "#userId")
    public List<ChatDTO> getUserChats(Long userId) {
        return this.repository.findByUser(userId)
            .stream()
            .map(this.chatSerializer::serialize)
            .toList();
    }

    @CachePut(cacheNames = "chat-messages", key = "#chatId")
    public List<MessageDTO> getChatMessages(Long chatId) {
        final Chat chat = this.repository.findById(chatId).orElseThrow();
        return chat.getMessages()
            .stream()
            .map(this.messageSerializer::serialize)
            .toList();
    }

}
