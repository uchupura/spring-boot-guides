package com.guide.jpa.OneToMany_단방향.repository;

import com.guide.jpa.OneToMany_단방향.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
