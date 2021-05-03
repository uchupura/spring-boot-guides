package com.guide.jpa.OneToMany_일반;

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
