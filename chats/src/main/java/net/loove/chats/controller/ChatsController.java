package net.loove.chats.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.loove.chats.dto.ChatDTO;
import net.loove.chats.dto.MessageDTO;
import net.loove.chats.service.ChatsService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ChatsController {

    private final ChatsService service;

    @MessageMapping("/chats")
    public void handleMessage(MessageDTO messageDTO) {
        this.service.handleMessage(messageDTO);
    }

    @GetMapping("/users/{userId}/chats")
    public List<ChatDTO> getChats(@PathVariable Long userId) {
        return this.service.getUserChats(userId);
    }

    @GetMapping("/chats/{chatId}/messages")
    public List<MessageDTO> getMessages(@PathVariable Long chatId) {
        return this.service.getChatMessages(chatId);
    }

}
