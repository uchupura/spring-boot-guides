package com.guide.event.domain.order;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreatedEvent {
    Long orderId;
    String memberName;
    int totalPrice;

    @Builder
    public OrderCreatedEvent(Long orderId, String memberName, int totalPrice) {
        this.orderId = orderId;
        this.memberName = memberName;
        this.totalPrice = totalPrice;
    }
}
