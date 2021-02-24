package com.guide.rabbitmq2.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

public class ConsumerConfig {
    private String queueName;
    private String routingKey;
    private int onOfConsumer;

    public int getOnOfConsumer() {
        return onOfConsumer;
    }

    public void setOnOfConsumer(int onOfConsumer) {
        this.onOfConsumer = onOfConsumer;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public ConsumerConfig(String queueName, String routingKey, int onOfConsumer, ConnectionFactory connectionFactory) throws Exception {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        DirectExchange exchange = new DirectExchange(queueName + ".exchange");
        rabbitAdmin.declareExchange(exchange);
        Queue queue = QueueBuilder.durable(queueName)
                .build();
        rabbitAdmin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
        rabbitAdmin.declareBinding(binding);

        this.queueName = queueName;
        this.routingKey = routingKey;
        this.onOfConsumer = onOfConsumer;
        ConsumerContainer container = new ConsumerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(this.queueName);
        container.setConcurrentConsumers(this.onOfConsumer);
        container.setMaxConcurrentConsumers(this.onOfConsumer);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener(new MessageListenerAdapter(new ConsumerHandler(), new Jackson2JsonMessageConverter()));
        container.start();
    }
}
