package com.guide.event.domain.order;

import lombok.Getter;

@Getter
public class OrderCreatedEvent {
    Order order;

    public OrderCreatedEvent(Order order) {
        this.order = order;
    }
}
