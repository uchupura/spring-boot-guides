package com.guide.rabbitmq2;

import com.guide.rabbitmq2.config.ConsumerConfig;
import com.guide.rabbitmq2.config.ProducerConfig;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpringRabbitMQApp /*implements CommandLineRunner*/ {
    public static void main(String[] args) {
        SpringApplication.run(SpringRabbitMQApp.class, args);
    }

    /*@Autowired
    private ConnectionFactory connectionFactory;
    @Override
    public void run(String... args) throws Exception {
        ProducerConfig producer = new ProducerConfig("ha.q1", "q1", connectionFactory);
        ConsumerConfig consumer = new ConsumerConfig("ha.q1", "q1", 5, connectionFactory);
        int cout = 0;
        while (true) {

            producer.send("카운트: " + cout);
            TimeUnit.SECONDS.sleep(1);
            cout++;

        }
    }*/
}
