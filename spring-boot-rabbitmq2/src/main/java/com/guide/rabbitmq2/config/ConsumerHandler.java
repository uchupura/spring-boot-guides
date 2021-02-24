package com.guide.rabbitmq2.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;

@Slf4j
public class ConsumerHandler implements ChannelAwareMessageListener {
    /*public void handleMessage(String text) {

        log.info("Received--------------------------: " + text);
        try {
            channel.basicNack(tag, false, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    public void onMessage(Message message, Channel channel) throws Exception {
        long tag = message.getMessageProperties().getDeliveryTag();
        log.info("Received--------------------------: " + new String(message.getBody()));
//        Thread.sleep(2000);
//        channel.basicAck(tag, false);
        /*if(message.getMessageProperties().getConsumerQueue().toString().equalsIgnoreCase("ha.q1")) {
            channel.basicNack(tag, false, true);
            Thread.sleep(5000);
        } else {
            channel.basicAck(tag, false);
        }*/
        Thread.sleep(1000*10);
    }
}
