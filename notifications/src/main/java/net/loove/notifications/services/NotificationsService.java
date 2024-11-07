package net.loove.notifications.services;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.loove.notifications.model.Notification;
import net.loove.notifications.repository.NotificationsRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationsService {

    private final NotificationsRepository repository;

    @CachePut(key = "#userId", cacheNames = "users_notifications", unless = "#result.isEmpty()")
    public Optional<List<Notification>> getUserNotifications(Long userId) {
        return this.repository.findByUserId(userId);
    }

    public void deleteNotification(Long id) {
        this.repository.deleteById(id);
    }

    public void saveNotification(Notification notification) {
        this.repository.save(notification);
    }

    @CachePut(key = "#id", cacheNames = "notifications", unless = "#result.isEmpty()")
    public Optional<Notification> getNotification(Long id) {
        return this.repository.findById(id);
    }

    public void sendNotification(Notification notification) {
        log.info("Sent notification: {}", notification);
        this.saveNotification(notification);

    }
}
