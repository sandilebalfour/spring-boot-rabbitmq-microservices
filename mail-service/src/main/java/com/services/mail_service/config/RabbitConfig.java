package com.services.mail_service.config;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "order.exchange";
    public static final String ROUTING_KEY = "order.created";
    public static final String QUEUE = "order.created.queue";

    @Bean Queue queue() { return new Queue(QUEUE, true); }
    @Bean TopicExchange exchange() { return new TopicExchange(EXCHANGE); }
    @Bean Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
    @Bean MessageConverter jsonMessageConverter() { return new Jackson2JsonMessageConverter(); }
}