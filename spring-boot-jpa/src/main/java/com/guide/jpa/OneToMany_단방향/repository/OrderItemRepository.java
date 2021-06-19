package com.guide.jpa.OneToMany_단방향.repository;

import com.guide.jpa.OneToMany_단방향.entity.Order;
import com.guide.jpa.OneToMany_단방향.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findOrderItemsByOrderIn(List<Order> orders);
}
