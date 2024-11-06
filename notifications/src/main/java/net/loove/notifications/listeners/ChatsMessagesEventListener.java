package net.loove.notifications.listeners;

import lombok.RequiredArgsConstructor;
import net.loove.notifications.events.ChatMessageEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatsMessagesEventListener {

    @KafkaListener(topics = "${events.topics.chat-messages}")
    public void handle(ChatMessageEvent messageEvent) {
        System.out.println("Received message: " + messageEvent);
    }

}
