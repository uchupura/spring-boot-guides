package com.guide.jpa.OneToMany_일반;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems where o.id = :id")
    Order findOrder(@Param("id") Long id);

    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems oi JOIN FETCH Item i ON oi.item = i.id where o.id = :id")
    Order findOrderWithItem(@Param("id") Long id);

    @Query("SELECT new com.guide.jpa.OneToMany_일반.OrderDto(o, oi) FROM Order o JOIN OrderItem oi ON o.id = oi.order JOIN FETCH Item i ON oi.item = i.id where o.id = :id")
    OrderDto findOrderItem(@Param("id") Long id);
}
