package com.guide.jpa.OneToMany_양방향.repository;

import com.guide.jpa.OneToMany_양방향.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
