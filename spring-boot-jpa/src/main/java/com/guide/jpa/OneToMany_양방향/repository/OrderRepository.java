package com.guide.jpa.OneToMany_양방향.repository;

import com.guide.jpa.OneToMany_양방향.dto.OrderDto;
import com.guide.jpa.OneToMany_양방향.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems where o.id = :id")
    Order findOrder(@Param("id") Long id);

    /**
     * Collection 객체를 fetch join한 경우 Collection의 개수만큼 결과가 뻥튀기된다.
     */
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems oi JOIN FETCH Item i ON oi.item = i.id")
    List<Order> findAllOrderWithItem();
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems oi JOIN FETCH Item i ON oi.item = i.id where o.id = :id")
    Order findOrderWithItem(@Param("id") Long id);

    @Query("SELECT new com.guide.jpa.OneToMany_양방향.dto.OrderDto(o, oi) FROM Order o JOIN OrderItem oi ON o.id = oi.order JOIN FETCH Item i ON oi.item = i.id where o.id = :id")
    OrderDto findOrderItem(@Param("id") Long id);
}
