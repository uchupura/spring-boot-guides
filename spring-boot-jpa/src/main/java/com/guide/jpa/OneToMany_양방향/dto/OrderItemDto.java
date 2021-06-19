package com.guide.jpa.OneToMany_양방향.dto;

import com.guide.jpa.OneToMany_양방향.entity.OrderItem;
import com.guide.jpa.OneToMany_양방향.entity.OrderStatus;
import lombok.Getter;

@Getter
public class OrderItemDto {
    private Long orderId;
    private OrderStatus orderStatus;
    private OrderItem orderItem;
    /*private int orderPrice;
    private int count;
    private String itemName;*/

    public OrderItemDto(Long orderId, OrderStatus orderStatus, OrderItem orderItem) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderItem = orderItem;
        /*this.orderPrice = orderPrice;
        this.count = count;
        this.itemName = itemName;*/
    }
}
