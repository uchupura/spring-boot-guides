package com.guide.jpa.OneToMany_단방향.repository;

import com.guide.jpa.OneToMany_단방향.entity.Order;
import com.guide.jpa.OneToMany_단방향.entity.OrderItem;
import com.guide.jpa.global.Querydsl4RepositorySupport;
import org.springframework.stereotype.Repository;

import static com.guide.jpa.OneToMany_단방향.entity.QOrder.order;
import static com.guide.jpa.OneToMany_단방향.entity.QOrderItem.orderItem;

@Repository(value = "OrderQueryRepository2")
public class OrderQueryRepository extends Querydsl4RepositorySupport {
    public OrderQueryRepository() {
        super(Order.class);
    }

    public OrderItem findOrderItem(Long id) {
        return select(orderItem)
                .from(orderItem)
                .join(orderItem.order, order).fetchJoin()
                .where(orderItem.id.eq(id))
                .fetchOne();
    }
}
