package com.guide.rabbitmq.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.stream.Collectors;

import static com.guide.rabbitmq.util.MessageUtil.*;

@RequiredArgsConstructor
@Configuration
public class RabbitMQConfiguration {

    private final ConnectionFactory connectionFactory;
    private final Environment environment;

    @Value("${spring.rabbitmq.queues}")
    private List<String> queues;

    @Bean
    public RabbitAdmin rabbitAdmin() { return new RabbitAdmin(connectionFactory); }

    @Bean
    public void createQueues() {

        queues.forEach(routingKey -> {
            DirectExchange exchange = createExchange(routingKey);
            addExchange(exchange);

            /*Queue queue = QueueBuilder.durable(QueueName(routingKey))
                    .withArgument("x-dead-letter-exchange", "")
                    .withArgument("x-dead-letter-routing-key", DeadLetterQueueName(routingKey))
                    .build();*/
            Queue queue = QueueBuilder.durable(QueueName(routingKey))
                    .build();
            addQueue(queue);

            addBinding(queue, exchange, routingKey);

            /*Queue dlq = QueueBuilder.durable(DeadLetterQueueName(routingKey)).build();
            addQueue(dlq);*/
        });
    }

    private DirectExchange createExchange(String queueName) { return new DirectExchange(ExchangeName(queueName)); }
    private void addExchange(AbstractExchange exchange) {
        rabbitAdmin().declareExchange(exchange);
    }
    private String addQueue(Queue queue) {
        return rabbitAdmin().declareQueue(queue);
    }
    private void addBinding(Queue queue, DirectExchange exchange, String routingKey) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
        rabbitAdmin().declareBinding(binding);
    }

    public List<String> listenerQueues() {
        return queues.stream().map(s -> QueueName(s)).collect(Collectors.toList());
    }
}
