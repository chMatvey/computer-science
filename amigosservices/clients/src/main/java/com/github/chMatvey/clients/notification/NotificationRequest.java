package com.github.chMatvey.clients.notification;

public record NotificationRequest(
        Long toCustomerId,
        String toCustomerName,
        String message
) {
}
