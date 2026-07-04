package com.services.mail_service;


import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@SpringBootApplication
public class MailServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailServiceApplication.class, args);
	}

//	@RabbitListener(queues = "order.created.queue")
//	public void handle(OrderCreatedEvent event) {
//		System.out.println("EMAIL SERVICE GOT: " + event);
//	}
	@RabbitListener(queues = "order.created.queue")
	public void handle(OrderCreatedEvent event) {
		System.out.println("=========================================");
		System.out.println("SENDING EMAIL TO: " + event.email());
		System.out.println("Subject: Order " + event.orderId() + " Confirmed");
		System.out.println("Body: Thanks for order #" + event.orderId());
		System.out.println("=========================================");
	}

	public record OrderCreatedEvent(Long orderId, String email) {}
}