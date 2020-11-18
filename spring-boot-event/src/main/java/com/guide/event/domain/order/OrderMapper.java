package com.guide.event.domain.order;

import com.guide.event.domain.order.OrderDTO.Request.Create;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target="member", ignore = true)
    @Mapping(target="totalPrice", ignore = true)
    Order map(Create source);

    @Mapping(source = "entity.member.id", target="memberId")
    @InheritInverseConfiguration
    OrderDTO.Response.Create map(Order entity);
}
