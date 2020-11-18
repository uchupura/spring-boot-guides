package com.guide.event.domain.member;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    Member map(MemberDTO.Request.Create source);

    MemberDTO.Response.Create map(Member source);
}
