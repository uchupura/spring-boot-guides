package com.guide.jpa.OneToMany_양방향_cascade.repository;

import com.guide.jpa.OneToMany_양방향_cascade.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
