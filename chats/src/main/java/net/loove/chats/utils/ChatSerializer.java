package net.loove.chats.utils;

import lombok.RequiredArgsConstructor;
import net.loove.chats.dto.ChatDTO;
import net.loove.chats.dto.MessageDTO;
import net.loove.chats.model.Chat;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatSerializer {

    private MessageSerializer messageSerializer;

    public ChatDTO serialize(Chat chat) {
        return ChatDTO.builder()
            .id(chat.getId())
            .users(chat.getUsers().toArray(new Long[0]))
            .messages(chat.getMessages()
                .stream()
                .map(this.messageSerializer::serialize)
                .toArray(MessageDTO[]::new))
            .build();
    }


}