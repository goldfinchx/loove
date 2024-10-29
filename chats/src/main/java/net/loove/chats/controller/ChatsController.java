package net.loove.chats.controller;

import lombok.RequiredArgsConstructor;
import net.loove.chats.service.ChatsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats")
public class ChatsController {

    private final ChatsService service;

}
