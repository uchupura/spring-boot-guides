package com.guide.rabbitmq2.service;

import com.guide.rabbitmq2.config.ConsumerContainer;
import com.guide.rabbitmq2.config.ConsumerHandler;
import com.guide.rabbitmq2.util.RabbitUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static com.guide.rabbitmq2.util.RabbitUtil.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitConsumerService {
    private static final String EXCHANGE = ".exchange";
    private static final int MAX_THREAD = 10;
    private RabbitAdmin rabbitAdmin;
    private final ConnectionFactory connectionFactory;

    @PostConstruct
    public void init() {
        rabbitAdmin = new RabbitAdmin(connectionFactory);
    }

    public void createConsumer(String queueName, int countOfConsumer) {
        DirectExchange exchange = createExchange(exchange(queueName));

        Queue queue = createQueue(queue(queueName));

        binding(routingKey(queueName), exchange, queue);

        ConsumerContainer container = new ConsumerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queue(queueName));
        container.setConcurrentConsumers(countOfConsumer);
        container.setMaxConcurrentConsumers(countOfConsumer + MAX_THREAD);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener(new MessageListenerAdapter(new ConsumerHandler(), new Jackson2JsonMessageConverter()));
        container.start();
    }

    private void binding(String routingKey, DirectExchange exchange, Queue queue) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
        rabbitAdmin.declareBinding(binding);
    }

    private Queue createQueue(String queueName) {
        Queue queue = QueueBuilder.durable(queueName).build();
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    private DirectExchange createExchange(String exchangeName) {
        DirectExchange exchange = new DirectExchange(exchangeName);
        rabbitAdmin.declareExchange(exchange);
        return exchange;
    }
}