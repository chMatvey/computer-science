package com.github.chMatvey.notification.repository;

import com.github.chMatvey.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
