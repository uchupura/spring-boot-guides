package com.guide.jpa.OneToMany_일반;

import com.guide.jpa.global.Querydsl4RepositorySupport;
import com.querydsl.core.types.Projections;
import org.springframework.stereotype.Repository;

import static com.guide.jpa.OneToMany_일반.QOrder.order;
import static com.guide.jpa.OneToMany_일반.QOrderItem.orderItem;

@Repository
public class OrderQueryRepository extends Querydsl4RepositorySupport {
    public OrderQueryRepository() {
        super(Order.class);
    }

    /**
     * @중요
     * from절의 객체(order)와 join된 객체(orderItem)만 DTO에서 사용할 수 있다.
     * DTO를 결과값으로 읽어올 때 fetchJoin()한 객체를 사용하면 [query specified join fetching, but the owner of the fetched association was not present in the select list] 에러 발생
     * DTO를 통해 조회할 경우 join()함수를 통해서 연결(orderItem)하고, join된 객체(orderItem.item)에 대해서는 fetchJoin을 통해서 데이터를 조회 가능하다.
     */
    public OrderDto findOrderItem(Long id) {
        return select(Projections.constructor(OrderDto.class, order, orderItem))
                .from(order)
                .join(orderItem).on(order.id.eq(orderItem.order.id))
                .join(orderItem.item).fetchJoin()
                .where(order.id.eq(id))
                .fetchOne();
    }

    /**
     * @중요
     * fetchJoin()한 객체(orderItem)의 멤버 변수를 DTO의 값으로 사용 시 에러 발생
     */
    public OrderItemDto findOrderAndOrderItem(Long id) {
        return select(Projections.constructor(OrderItemDto.class, order.id, order.status, orderItem))
                .from(order)
                .join(orderItem).on(order.id.eq(orderItem.order.id))
                .join(orderItem.item).fetchJoin()
                .where(order.id.eq(id))
                .fetchOne();
    }
}
