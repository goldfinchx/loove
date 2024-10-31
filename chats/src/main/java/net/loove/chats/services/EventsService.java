package net.loove.chats.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.loove.chats.configs.EventsConfigurationProperties;
import net.loove.chats.events.NotificationEvent;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventsService {

    private final KafkaTemplate<String, NotificationEvent> template;
    private final EventsConfigurationProperties properties;
    private final Environment environment;

    public void publishNotificationEvent(NotificationEvent notificationEvent) {
        this.template.send(this.properties.getNotificationsTopic(), notificationEvent);
    }

}
