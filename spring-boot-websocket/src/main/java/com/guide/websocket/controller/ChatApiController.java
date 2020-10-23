package com.guide.websocket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guide.websocket.config.WebSocketClient;
import com.guide.websocket.model.Message;
import com.guide.websocket.model.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatApiController {

    private final WebSocketClient client;
    private final ObjectMapper objectMapper;

    @GetMapping
    public void sendMessage(@RequestParam("message") String content) {
        Message message = new Message();
        message.setContent(content);
        message.setSender("system");
        message.setType("CHAT");

        try {
            StompSession session = client.connect();
            String s = objectMapper.writeValueAsString(message);
            Message readValue = objectMapper.readValue(s, Message.class);
            session.send("/app/chat.sendMessage", message);
            client.close(session);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
