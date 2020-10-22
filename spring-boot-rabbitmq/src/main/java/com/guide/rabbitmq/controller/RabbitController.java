package com.guide.rabbitmq.controller;

import com.guide.rabbitmq.domain.Notification;
import com.guide.rabbitmq.service.RabbitMQProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(RabbitController.URI)
public class RabbitController {

    public static final String URI = "/api/messages";

    private final RabbitMQProducer producer;

    @PostMapping("noti")
    public void notification(@RequestBody Notification notification) {

        producer.send("notification", notification);
    }

    @PostMapping("chat")
    public void chat(@RequestBody Notification notification) {

        producer.send("chat", notification);
    }
}
