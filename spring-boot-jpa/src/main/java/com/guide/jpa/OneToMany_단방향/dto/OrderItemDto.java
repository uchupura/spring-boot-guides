package com.guide.jpa.OneToMany_단방향.dto;

import com.guide.jpa.OneToMany_단방향.dto.ItemDto;
import com.guide.jpa.OneToMany_단방향.entity.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemDto {
    private Long id;
    private int orderPrice;
    private int count;
    private ItemDto item;

    public OrderItemDto(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
        this.item = new ItemDto(orderItem.getItem());
    }
}
