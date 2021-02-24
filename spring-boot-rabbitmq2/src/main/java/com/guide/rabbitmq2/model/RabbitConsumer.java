package com.guide.rabbitmq2.model;

import lombok.Getter;

@Getter
public class RabbitConsumer {
    private String queueName;
    private Integer countOfConsumer;
    private String message;
}
