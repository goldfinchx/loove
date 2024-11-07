package net.loove.notifications.repository;

import java.util.List;
import java.util.Optional;
import net.loove.notifications.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.receiver = :userId")
    Optional<List<Notification>> findByUserId(Long userId);
}
