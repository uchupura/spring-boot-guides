package com.guide.jpa.domain.chatroom;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ChatRoomAPI.URL)
public class ChatRoomAPI {
    public static final String URL = "/v1/chatroom";
    private final ChatRoomRepository chatRoomRepository;

    @PostMapping
    public void create(@RequestBody ChatRoomDTO.Request.ChatRoom body) {
        /*if (chatRoom instanceof ChatRoomDTO.Request.SmsChatRoom) {
            SmsChatRoom map = SmsChatRoomMapper.INSTANCE.map((ChatRoomDTO.Request.SmsChatRoom)chatRoom);
        }*/

        ChatRoom chatRoom = ChatRoomFactory.create(body);
        System.out.println(chatRoom);
//        System.out.println(((SmsChatRoom)chatRoom).getDomainId());
        chatRoomRepository.save(chatRoom);
    }

    @GetMapping
    public List<ChatRoomDTO.Request.ChatRoom> getChatRooms() {
        List<ChatRoom> rooms = chatRoomRepository.findAll();
        return rooms.stream().map(r -> ChatRoomFactory.create(r)).collect(Collectors.toList());
    }
}