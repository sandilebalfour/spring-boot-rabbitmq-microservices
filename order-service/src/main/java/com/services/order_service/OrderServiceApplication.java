package com.services.order_service;



import com.services.order_service.config.RabbitConfig;
import com.services.order_service.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class OrderServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
	public record OrderCreatedEvent(Long orderId, String email) {}
}

@RestController
@RequiredArgsConstructor
@Slf4j
class OrderController {
	private final RabbitTemplate rabbit;

	@PostMapping("/orders")
	public String createOrder(@RequestBody OrderRequest req) {
		log.info(">>> Creating order for {}", req.email());
		rabbit.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY,
				new OrderCreatedEvent(req.orderId(), req.email()));
		return "Order " + req.orderId() + " sent to queue";
	}
	public record OrderRequest(Long orderId, String email) {}
}