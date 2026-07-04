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

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue queue() { return new Queue("order.created.queue", true); }

    @Bean
    public TopicExchange exchange() { return new TopicExchange("orders.exchange"); }

    @Bean
    public Binding binding(Queue q, TopicExchange e) {
        return BindingBuilder.bind(q).to(e).with("orders.created");
    }}