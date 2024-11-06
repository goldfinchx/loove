package net.loove.chats.services;

import lombok.RequiredArgsConstructor;
import net.loove.chats.configs.properties.EventsTopicsProperties;
import net.loove.chats.events.ChatMessageEvent;
import net.loove.chats.events.Event;
import net.loove.chats.model.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventsService {

    private final KafkaTemplate<String, Event> template;
    private final EventsTopicsProperties topicsProperties;


    public void publishEvent(Event event, String topic) {
        this.template.send(topic, event);
    }

    public void publishChatMessageEvent(Message message) {
        final Event event = ChatMessageEvent.builder()
            .chatId(message.getChat().getId())
            .sender(message.getSender())
            .receiver(message.getReceiver())
            .body(message.getContent())
            .timestamp(message.getAt().toEpochMilli())
            .build();

        this.publishEvent(event, this.topicsProperties.getChatMessagesTopic());
    }


}
