package com.services.order_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// using orderId from request. If you want auto-gen, use @GeneratedValue
    private Long id;
    private String email;

    private String status;
}