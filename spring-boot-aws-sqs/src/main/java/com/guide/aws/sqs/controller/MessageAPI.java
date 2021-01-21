package com.guide.aws.sqs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guide.aws.sqs.dto.Message;
import com.guide.aws.sqs.infra.AmazonSQSSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageAPI {
    private final ObjectMapper objectMapper;
    private final AmazonSQSSender amazonSQSSender;

    @PostMapping("/send")
    public String send(@RequestBody Message message) throws JsonProcessingException {
        amazonSQSSender.sendMessage(message);
        return "OK";
    }

    @SqsListener(value = "${cloud.aws.sqs.queue.name}")
    private void receiveMessage(@Headers Map<String, String> header, @Payload String message) throws JsonProcessingException {
        Message readValue = objectMapper.readValue(message, Message.class);
        System.out.println(readValue.getType());
        System.out.println(readValue.getMsg());
    }
}
