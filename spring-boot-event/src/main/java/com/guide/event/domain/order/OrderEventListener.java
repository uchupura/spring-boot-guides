package com.guide.event.domain.order;

import com.guide.event.domain.order.Order;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    @EventListener
    public void handle(Order order) {
        System.out.println(">>>>> Order");
        //throw new RuntimeException("Entity RuntimeException!!");
    }
}
