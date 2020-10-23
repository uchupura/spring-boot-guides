package com.guide.websocket.controller;

import com.guide.websocket.model.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("chat.sendMessage/{chatRoomId}")
    @SendTo("/topic/public.{chatRoomId}")
    public Message sendMessage(@Payload Message message, @DestinationVariable Long chatRoomId) {
        return message;
    }

    @MessageMapping("chat.addUser/{chatRoomId}")
    @SendTo("/topic/public.{chatRoomId}")
    public Message addUser(@Payload Message message, StompHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }
}
