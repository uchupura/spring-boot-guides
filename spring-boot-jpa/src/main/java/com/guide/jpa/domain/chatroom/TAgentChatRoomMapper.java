package com.guide.jpa.domain.chatroom;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TAgentChatRoomMapper {
    TAgentChatRoomMapper INSTANCE = Mappers.getMapper(TAgentChatRoomMapper.class);

    TAgentChatRoom map(ChatRoomDTO.Request.TAgentChatRoom source);

    ChatRoomDTO.Request.TAgentChatRoom map(TAgentChatRoom source);
}
