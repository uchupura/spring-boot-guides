package com.guide.jpa.OneToMany_양방향.dto;

import com.guide.jpa.OneToMany_양방향.entity.Order;
import com.guide.jpa.OneToMany_양방향.entity.OrderItem;
import lombok.Getter;

@Getter
public class OrderDto {
    private Order order;
    private OrderItem item;
    public OrderDto(Order order, OrderItem item) {
        this.order = order;
        this.item = item;
    }
    /*private OrderItem orderItem;
    public OrderDto(OrderItem orderItem) {
        this.orderItem = orderItem;
    }*/
}
