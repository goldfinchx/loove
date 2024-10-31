package net.loove.chats.events;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationEvent implements Serializable {

    private Long userId;
    private String title;
    private String body;
    private Long timestamp;
    private String service;

    public NotificationEvent(Long userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.timestamp = System.currentTimeMillis();
        this.service = "chats";
    }

}
