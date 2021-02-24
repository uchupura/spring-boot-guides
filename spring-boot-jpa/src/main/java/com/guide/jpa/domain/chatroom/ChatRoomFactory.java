package com.guide.jpa.domain.chatroom;

public class ChatRoomFactory {
    public static ChatRoom create(ChatRoomDTO.Request.ChatRoom room) {
        ChatRoom chatroom = null;
        if (room instanceof ChatRoomDTO.Request.SmsChatRoom) {
            chatroom = SmsChatRoomMapper.INSTANCE.map((ChatRoomDTO.Request.SmsChatRoom) room);
        } else if (room instanceof ChatRoomDTO.Request.TAgentChatRoom) {
            chatroom = TAgentChatRoomMapper.INSTANCE.map((ChatRoomDTO.Request.TAgentChatRoom) room);
        } else {
            return null;
        }
        return chatroom;
    }

    public static ChatRoomDTO.Request.ChatRoom create(ChatRoom room) {
        ChatRoomDTO.Request.ChatRoom chatroom = null;
        if (room instanceof SmsChatRoom) {
            chatroom = SmsChatRoomMapper.INSTANCE.map((SmsChatRoom) room);
        } else if (room instanceof TAgentChatRoom) {
            chatroom = TAgentChatRoomMapper.INSTANCE.map((TAgentChatRoom) room);
        } else {
            return null;
        }
        return chatroom;
    }
}
