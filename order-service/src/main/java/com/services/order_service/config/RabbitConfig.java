package com.services.order_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE = "order.created.queue";
    public static final String EXCHANGE = "order.exchange";
    public static final String ROUTING_KEY = "order.created";

    @Bean public Queue queue() { return new Queue(QUEUE, true); }
    @Bean public TopicExchange exchange() { return new TopicExchange(EXCHANGE); }
    @Bean public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
    @Bean // <- also add this so you send JSON not Java serialized bytes
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}