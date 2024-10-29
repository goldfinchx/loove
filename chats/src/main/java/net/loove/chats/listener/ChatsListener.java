package net.loove.chats.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.loove.chats.dto.MessageDTO;
import net.loove.chats.service.ChatsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatsListener {

    private final ChatsService service;

    @RabbitListener(id = "${rabbitmq.exchange-name}")
    public void onMessage(MessageDTO messageDto) {
        this.service.handleMessage(messageDto);
    }

}
