package com.guide.jpa.OneToMany_양방향_cascade.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "order_item3")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // @JoinColumn에는 임의의 FK 이름 입력
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    @Builder
    private OrderItem(int orderPrice, int count) {
        this.orderPrice = orderPrice;
        this.count = count;
    }

    /**
     * 생성 메서드
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = OrderItem.builder()
                .orderPrice(orderPrice)
                .count(count)
                .build();
        orderItem.setItem(item);
        return orderItem;
    }
    /**
     * 연관관계 메서드
     */
    public void setOrder(Order order) {
        this.order = order;
    }
    public void setItem(Item item) {
        this.item = item;
        item.addOrderItem(this);
    }
}