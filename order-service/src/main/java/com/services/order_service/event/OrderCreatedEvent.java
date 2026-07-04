package com.services.order_service.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter @Setter @NoArgsConstructor
public class OrderCreatedEvent implements Serializable {
    private Long orderId;
    private String email;
    public OrderCreatedEvent(Long orderId, String email) {
        this.orderId = orderId; this.email = email;
    }
}