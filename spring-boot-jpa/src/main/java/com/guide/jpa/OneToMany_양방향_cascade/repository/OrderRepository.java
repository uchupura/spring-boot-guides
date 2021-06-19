package com.guide.jpa.OneToMany_양방향_cascade.repository;

import com.guide.jpa.OneToMany_양방향_cascade.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
