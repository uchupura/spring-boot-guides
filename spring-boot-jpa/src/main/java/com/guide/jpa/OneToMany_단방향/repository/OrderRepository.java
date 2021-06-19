package com.guide.jpa.OneToMany_단방향.repository;

import com.guide.jpa.OneToMany_단방향.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
