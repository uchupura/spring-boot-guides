package com.guide.rabbitmq.util;

import org.springframework.core.env.Environment;

import java.util.Arrays;

public class MessageUtil {

    private static final String HA = "ha.";
    private static final String DLQ = ".dlq";
    private static final String QUEUE = ".queue";
    private static final String EXCHANGE = ".exchange";

    public static String QueueName(String queue) {
        return HA + queue + QUEUE;
    }

    public static String DeadLetterQueueName(String queue) {
        return HA + queue + DLQ;
    }

    public static String ExchangeName(String queue) {
        return queue + EXCHANGE;
    }

    public static String getActiveProfile(Environment env) {

        return Arrays.asList(env.getActiveProfiles()).size() > 0 ? Arrays.asList(env.getActiveProfiles()).get(0) : "default";
    }
}
