package com.guide.rabbitmq2.controller;

import com.guide.rabbitmq2.model.RabbitConsumer;
import com.guide.rabbitmq2.service.RabbitConsumerService;
import com.guide.rabbitmq2.util.RabbitUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.guide.rabbitmq2.util.RabbitUtil.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(RabbitController.URL)
public class RabbitController {
    public static final String URL = "/v1/rabbit";

    private final AmqpTemplate amqpTemplate;
    private final RabbitConsumerService rabbitConsumerService;

    @PostMapping
    public void createConsumer(@RequestBody RabbitConsumer body) {
        rabbitConsumerService.createConsumer(body.getQueueName(), body.getCountOfConsumer());
    }

    @PostMapping("/message")
    public void sendMessage(@RequestBody RabbitConsumer body) {
        amqpTemplate.convertAndSend(exchange(body.getQueueName()), routingKey(body.getQueueName()), body.getMessage());
    }
}