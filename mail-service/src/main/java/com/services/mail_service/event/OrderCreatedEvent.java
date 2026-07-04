package com.services.mail_service.event;

public record OrderCreatedEvent(long orderId, String customerEmail) {
}
