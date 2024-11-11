package net.loove.notifications.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import net.loove.notifications.model.Notification;
import net.loove.notifications.repository.NotificationsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotificationsServiceTest {

    @Mock
    private NotificationsRepository repository;

    @Mock
    private Logger logger;

    @InjectMocks
    private NotificationsService service;

    private Notification notification;

    @BeforeEach
    void setUp() {
        this.notification = Notification.builder()
            .id(1L)
            .receiver(1L)
            .body("Test Notification")
            .title("Test Title")
            .build();
    }


    @Test
    @Order(1)
    void saveNotification_shouldSaveNotification() {
        when(this.repository.save(this.notification)).thenReturn(this.notification);

        final Notification savedNotification = this.service.saveNotification(this.notification);
        verify(this.repository).save(this.notification);
        assertEquals(this.notification, savedNotification);
    }

    @Test
    @Order(2)
    void getUserNotifications_shouldReturnList() {
        when(this.repository.findByUserId(this.notification.getReceiver())).thenReturn(Optional.of(List.of(this.notification)));

        final Optional<List<Notification>> notifications = this.service.getUserNotifications(this.notification.getReceiver());
        assertTrue(notifications.isPresent());
        assertEquals(1, notifications.get().size());
        assertEquals(this.notification, notifications.get().getFirst());
    }

    @Test
    @Order(3)
    void getNotification_shouldReturnNotification() {
        when(this.repository.findById(this.notification.getId())).thenReturn(Optional.of(this.notification));

        final Optional<Notification> notification = this.service.getNotification(this.notification.getId());
        assertTrue(notification.isPresent());
        assertEquals(this.notification, notification.get());
    }

    @Test
    @Order(4)
    void sendNotification_shouldSaveNotification() {
        this.service.sendNotification(this.notification);
        verify(this.repository).save(this.notification);
    }

    @Test
    @Order(5)
    void deleteNotification_shouldDeleteNotification() {
        this.service.deleteNotification(this.notification.getId());
        verify(this.repository).deleteById(this.notification.getId());
    }

}