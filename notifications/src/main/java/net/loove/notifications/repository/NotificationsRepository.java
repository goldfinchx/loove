package net.loove.notifications.repository;

import java.util.List;
import net.loove.notifications.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
}
