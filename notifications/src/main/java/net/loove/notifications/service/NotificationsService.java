package net.loove.notifications.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.loove.notifications.model.Notification;
import net.loove.notifications.repository.NotificationsRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

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


    @CachePut(key = "#id", cacheNames = "notifications", unless = "#result.isEmpty()")
    public Optional<Notification> getNotification(Long id) {
        return this.repository.findById(id);
    }
}
