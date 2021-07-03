package com.guide.kafka.controller;

import com.guide.kafka.service.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
@RequiredArgsConstructor
public class KafkaController {
    private final KafkaProducer producer;

    @GetMapping
    public String sendMessage(@RequestParam("message") String message) {
        producer.sendMessage(message);
        return "success";
    }
}
