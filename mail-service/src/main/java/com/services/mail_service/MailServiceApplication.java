package com.services.mail_service;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.services.mail_service.event.OrderCreatedEvent;
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
		 // this will show OrderCreatedEvent[orderId=101, email=null]
		System.out.println("EMAIL FIELD: " + event.getEmail());
		System.out.println("Subject: Order " + event.getOrderId() + " Confirmed");
	}

}