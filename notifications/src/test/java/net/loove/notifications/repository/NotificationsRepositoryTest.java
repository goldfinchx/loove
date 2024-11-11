package net.loove.notifications.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.netflix.discovery.converters.Auto;
import java.util.List;
import java.util.Optional;
import net.loove.notifications.model.Notification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@DataJpaTest
class NotificationsRepositoryTest {

    private Notification notification;

    @Autowired
    private NotificationsRepository repository;

    @BeforeEach
    void setUp() {
        this.notification = Notification.builder()
            .id(1L)
            .receiver(1L)
            .body("Test Notification")
            .title("Test Title")
            .build();

        this.repository.save(this.notification);
    }

    @AfterEach
    void tearDown() {
        this.repository.deleteAll();
    }

    @Test
    void findByUserId_shouldReturnList() {
        final Optional<List<Notification>> result = this.repository.findByUserId(this.notification.getReceiver());
        assertTrue(result.isPresent());
    }
}