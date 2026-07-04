package com.services.order_service;

import com.services.order_service.config.RabbitConfig;
import com.services.order_service.event.OrderCreatedEvent;
import com.services.order_service.model.Order;
import com.services.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter; // ADD THIS
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class OrderServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean // ADD THIS so Rabbit sends JSON
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}

@RestController
@RequiredArgsConstructor
@Slf4j
class OrderController {
	private final RabbitTemplate rabbit;
	private final OrderRepository orderRepository;

	@PostMapping("/orders")
	public String createOrder(@RequestBody OrderRequest req) {
		Order order = Order.builder()
				.email(req.email())
				.status("NEW")
				.build();
		orderRepository.save(order); // order.getId() is now set

		log.info(">>> Saved order {} to DB", order.getId());

		// FIX: send order.getId() not req.orderId()
		rabbit.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY,
				new OrderCreatedEvent(order.getId(), order.getEmail()));

		return "Order " + order.getId() + " saved and sent to queue";
	}

	public record OrderRequest(String email) {} // remove orderId from request
}