package com.guide.rabbitmq2.util;

public class RabbitUtil {
    public static String queue(String queueName) {
        return "ha." + queueName;
    }
    public static String exchange(String queueName) {
        return queueName + ".exchange";
    }

    public static String routingKey(String queueName) {
        return queueName;
    }
}
