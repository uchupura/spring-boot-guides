package com.guide.jpa.OneToMany_양방향.repository;

import com.guide.jpa.OneToMany_양방향.dto.OrderDto;
import com.guide.jpa.OneToMany_양방향.dto.OrderItemDto;
import com.guide.jpa.OneToMany_양방향.entity.Order;
import com.guide.jpa.global.Querydsl4RepositorySupport;
import com.querydsl.core.types.Projections;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.guide.jpa.OneToMany_양방향.entity.QItem.item;
import static com.guide.jpa.OneToMany_양방향.entity.QOrder.order;
import static com.guide.jpa.OneToMany_양방향.entity.QOrderItem.orderItem;


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
     *
     * select
     *             order0_.id as col_0_0_,
     *             order0_.status as col_1_0_,
     *             orderitem1_.order_item_id as col_2_0_,
     *             item2_.item_id as item_id1_1_2_,
     *             orderitem1_.order_item_id as order_it1_4_0_,
     *             item2_.item_id as item_id1_1_1_,
     *             orderitem1_.count as count2_4_0_,
     *             orderitem1_.item_id as item_id4_4_0_,
     *             orderitem1_.order_id as order_id5_4_0_,
     *             orderitem1_.order_price as order_pr3_4_0_,
     *             item2_.name as name2_1_1_,
     *             item2_.price as price3_1_1_,
     *             item2_.stock_quantity as stock_qu4_1_1_
     *         from
     *             orders order0_
     *         inner join
     *             order_item orderitem1_
     *                 on (
     *                     order0_.id=orderitem1_.order_id
     *                 )
     *         inner join
     *             item item2_
     *                 on orderitem1_.item_id=item2_.item_id
     *         where
     *             order0_.id=?
     */
    public OrderItemDto findOrderAndOrderItem(Long id) {
        return select(Projections.constructor(OrderItemDto.class, order.id, order.status, orderItem))
                .from(order)
                .join(orderItem).on(order.id.eq(orderItem.order.id))
                .join(orderItem.item).fetchJoin()
                .where(order.id.eq(id))
                .fetchOne();
    }

    /**
     * select
     *             order0_.id as col_0_0_,
     *             order0_.status as col_1_0_,
     *             orderitem1_.order_item_id as col_2_0_,
     *             orderitem1_.order_item_id as order_it1_4_,
     *             orderitem1_.count as count2_4_,
     *             orderitem1_.item_id as item_id4_4_,
     *             orderitem1_.order_id as order_id5_4_,
     *             orderitem1_.order_price as order_pr3_4_
     *         from
     *             orders order0_
     *         inner join
     *             order_item orderitem1_
     *                 on (
     *                     order0_.id=orderitem1_.order_id
     *                 )
     *         inner join
     *             item item2_
     *                 on orderitem1_.item_id=item2_.item_id
     *         where
     *             order0_.id=?
     */
    public OrderItemDto findOrderAndOrderItem2(Long id) {
        return select(Projections.constructor(OrderItemDto.class, order.id, order.status, orderItem))
                .from(order)
                .join(orderItem).on(order.id.eq(orderItem.order.id))
                .join(orderItem.item)
                .where(order.id.eq(id))
                .fetchOne();
    }

    public List<Order> findAllWithItem() {
        return select(order).distinct()
                .from(order)
                .join(order.orderItems, orderItem).fetchJoin()
                .join(orderItem.item, item).fetchJoin()
                .fetch();
    }
}
