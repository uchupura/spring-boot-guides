package com.guide.rabbitmq2.config;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

public class ConsumerContainer extends SimpleMessageListenerContainer {
    public void start() {
        super.doStart();
    }
}
