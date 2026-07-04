package com.services.order_service.event;

public record OrderCreatedEvent(Long orderId, String customerEmail) {
}
