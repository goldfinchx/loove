package net.loove.notifications.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.loove.notifications.model.Notification;
import net.loove.notifications.service.NotificationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class NotificationsController {

    private final NotificationsService service;

    @GetMapping("users/{userId}/notifications")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable Long userId) {
        return ResponseEntity.of(this.service.getUserNotifications(userId));
    }

    @DeleteMapping("notifications/{id}")
    public ResponseEntity deleteNotification(@PathVariable Long id) {
        this.service.deleteNotification(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("notifications/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable Long id) {
        return ResponseEntity.of(this.service.getNotification(id));
    }

}
