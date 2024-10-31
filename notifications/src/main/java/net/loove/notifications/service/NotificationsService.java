package net.loove.notifications.service;

import lombok.RequiredArgsConstructor;
import net.loove.notifications.repository.NotificationsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationsService {

    private final NotificationsRepository repository;

}
