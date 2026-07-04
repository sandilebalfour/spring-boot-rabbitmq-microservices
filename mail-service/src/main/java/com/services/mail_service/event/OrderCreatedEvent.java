package com.services.mail_service.event;
// same package in both

import java.io.Serializable;

public class OrderCreatedEvent implements Serializable {
    private Long orderId;
    private String email;

    public OrderCreatedEvent() {} // default constructor for Jackson

    public OrderCreatedEvent(Long orderId, String email) {
        this.orderId = orderId;
        this.email = email;
    }

    public Long getOrderId() { return orderId; }
    public String getEmail() { return email; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "OrderCreatedEvent{orderId=" + orderId + ", email='" + email + "'}";
    }
}