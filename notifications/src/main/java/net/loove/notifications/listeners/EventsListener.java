package net.loove.notifications.listeners;

import lombok.RequiredArgsConstructor;
import net.loove.notifications.dtos.Event;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventsListener {

    @KafkaListener(topics = "#{'${notifications.topics}'.split(',')}")
    public void listen(@Payload Event message) {
        System.out.println("Received message: " + message);
    }

}
