package com.guide.rabbitmq.service;

import com.guide.rabbitmq.config.RabbitMQConfiguration;
import com.guide.rabbitmq.domain.Notification;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class RabbitMQConsumer {

    private RabbitMQConfiguration rabbitMQConfiguration;

    @RabbitListener(queues = "#{rabbitMQConfiguration.listenerQueues()}")
    public void process(@Payload Notification notification, Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("Processing at \'{}\' queue \'{}\' payload \'{}\'", new Date(), message.getMessageProperties().getConsumerQueue().toString(), notification.toString());
        if (notification.isHasError()) {
            throw new NullPointerException("test");
        }
    }
}
