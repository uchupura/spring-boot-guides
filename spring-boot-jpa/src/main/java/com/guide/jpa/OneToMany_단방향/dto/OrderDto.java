package com.guide.jpa.OneToMany_단방향.dto;

import com.guide.jpa.OneToMany_단방향.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderDto {
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus status;

    public OrderDto(Long id, LocalDateTime orderDate, OrderStatus status) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
    }
}
