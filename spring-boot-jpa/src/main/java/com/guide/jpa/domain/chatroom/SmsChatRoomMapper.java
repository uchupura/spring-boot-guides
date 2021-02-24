package com.guide.jpa.domain.chatroom;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SmsChatRoomMapper {
    SmsChatRoomMapper INSTANCE = Mappers.getMapper(SmsChatRoomMapper.class);

    SmsChatRoom map(ChatRoomDTO.Request.SmsChatRoom source);

    ChatRoomDTO.Request.SmsChatRoom map(SmsChatRoom source);
}
