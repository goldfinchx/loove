package net.loove.notifications.repository;

import java.util.List;
import java.util.Optional;
import net.loove.notifications.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository extends JpaRepository<Notification, Long> {
    Optional<List<Notification>> findByUserId(Long userId);
}
