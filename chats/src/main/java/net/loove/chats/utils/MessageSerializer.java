package net.loove.chats.utils;

import net.loove.chats.dto.MessageDTO;
import net.loove.chats.model.Chat;
import net.loove.chats.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageSerializer {

    public Message deserialize(MessageDTO messageDto, Chat chat) {
        return Message.builder()
            .chat(chat)
            .sender(messageDto.getSender())
            .content(messageDto.getContent())
            .at(messageDto.getAt())
            .build();
    }

    public MessageDTO serialize(Message message) {
        return MessageDTO.builder()
            .id(message.getId())
            .receiver(message.getReceiver())
            .sender(message.getSender())
            .content(message.getContent())
            .at(message.getAt())
            .build();
    }

}
