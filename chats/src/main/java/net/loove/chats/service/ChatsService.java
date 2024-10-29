package net.loove.chats.service;

import lombok.RequiredArgsConstructor;
import net.loove.chats.repository.ChatsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatsService {

    private final ChatsRepository repository;

}
