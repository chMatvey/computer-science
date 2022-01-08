package com.github.chMatvey.notification.service;

import com.github.chMatvey.clients.notification.NotificationRequest;
import com.github.chMatvey.notification.model.Notification;
import com.github.chMatvey.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
public record NotificationService(NotificationRepository notificationRepository) {
    public void send(NotificationRequest request) {
        Notification notification = Notification.builder()
                .toCustomerId(request.toCustomerId())
                .toCustomerEmail(request.toCustomerName())
                .sender("Notification Service")
                .message(request.message())
                .sentAt(now())
                .build();
        notificationRepository.save(notification);
    }
}
