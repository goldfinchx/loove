package net.loove.notifications.listeners;

import lombok.RequiredArgsConstructor;
import net.loove.notifications.dtos.ProfileDTO;
import net.loove.notifications.events.ChatMessageEvent;
import net.loove.notifications.model.Notification;
import net.loove.notifications.services.NotificationsService;
import net.loove.notifications.services.ProfilesService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatsMessagesEventListener {

    private final NotificationsService service;
    private final ProfilesService profilesService;

    @KafkaListener(topics = "${events.topics.chat-messages}")
    public void handle(ChatMessageEvent messageEvent) {
        final ProfileDTO senderProfile = this.profilesService.getProfile(messageEvent.getSender());
        final String title = "New message from " + senderProfile.getName();

        final Notification notification = Notification.builder()
            .receiver(messageEvent.getReceiver())
            .body(messageEvent.getBody())
            .title(title)
            .build();

        this.service.sendNotification(notification);
    }

}
