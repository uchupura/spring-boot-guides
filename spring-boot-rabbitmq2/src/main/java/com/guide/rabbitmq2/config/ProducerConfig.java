package com.guide.rabbitmq2.config;

import com.guide.rabbitmq2.model.Notification;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

public class ProducerConfig {
    private String queueName;
    private String routingKey;
    private RabbitTemplate rabbitTemplate;

    private ConnectionFactory connectionFactory;

    public ProducerConfig() {
    }

    public ProducerConfig(String queueName, String routingKey, ConnectionFactory connectionFactory) {
        this.queueName = queueName;
        this.routingKey = routingKey;
        this.connectionFactory = connectionFactory;
        this.rabbitTemplate = rabbitTemplate();
        RabbitAdmin admin = new RabbitAdmin(this.rabbitTemplate.getConnectionFactory());
        admin.declareQueue(new Queue(this.queueName));
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }
    public String getQueueName() {
        return queueName;
    }

    public String getRoutingKey() {
        return routingKey;
    }
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//The routing key is set to the name of the queue by the broker for the default exchange.
        template.setRoutingKey(this.routingKey);
        template.setExchange(this.queueName + ".exchange");
//Where we will synchronously receive messages from
        template.setDefaultReceiveQueue(this.queueName);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    /*public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("172.21.115.16,172.21.112.205");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }*/

    public void send(String s) {
        Notification notification = new Notification();
        notification.setMessage(s);
        notification.setHasError(false);
        this.rabbitTemplate.convertAndSend(notification);
    }
}
