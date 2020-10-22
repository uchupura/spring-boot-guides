package com.guide.rabbitmq.service;

import com.guide.rabbitmq.domain.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import static com.guide.rabbitmq.util.MessageUtil.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class RabbitMQProducer {

    private final AmqpTemplate amqpTemplate;

    public void send(String routingKey, Notification notification) {
        amqpTemplate.convertAndSend(ExchangeName(routingKey), routingKey, notification);
    }
}
