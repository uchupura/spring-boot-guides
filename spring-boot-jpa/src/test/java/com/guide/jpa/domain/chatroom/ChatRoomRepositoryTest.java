package com.guide.jpa.domain.chatroom;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class ChatRoomRepositoryTest {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    public void SMS_챗룸_생성() throws Exception {
        ChatRoomDTO.Request.SmsChatRoom smsChatRoom = ChatRoomDTO.Request.SmsChatRoom.builder()
                .categoryId(1l)
                .counselorId(2l)
                .domainId(3l)
                .build();
        chatRoomRepository.save(ChatRoomFactory.create(smsChatRoom));
    }

    @Test
    public void TAGENT_챗룸_생성() throws Exception {
        ChatRoomDTO.Request.TAgentChatRoom tAgentChatRoom = ChatRoomDTO.Request.TAgentChatRoom.builder()
                .categoryId(1l)
                .counselorId(2l)
                .name("T에이전트")
                .build();
        chatRoomRepository.save(ChatRoomFactory.create(tAgentChatRoom));
    }

    @Test
    public void 챗룸_리스트_검색() throws Exception {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        System.out.println(chatRooms.toString());
    }
}