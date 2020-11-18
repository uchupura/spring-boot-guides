package com.guide.event.domain.order;

import com.guide.event.domain.member.Member;
import com.guide.event.global.config.event.PublishEvent;
import com.guide.event.global.config.event.Event;
import lombok.*;

import javax.persistence.*;

@Getter
@Table(name = "orders")
@Entity
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String itemName;

    private int price;

    private int count;

    private int totalPrice;

    @Builder
    public Order(Member member, String itemName, int price, int count, int totalPrice) {
        this.member = member;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
        this.totalPrice = totalPrice;
    }

    @PostPersist
    public void postPersist() {
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(id)
                .memberName(member.getName())
                .totalPrice(totalPrice)
                .build();
        Event.publish(event);
    }
}
