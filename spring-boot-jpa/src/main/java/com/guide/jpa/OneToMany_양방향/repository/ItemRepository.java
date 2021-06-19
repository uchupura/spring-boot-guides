package com.guide.jpa.OneToMany_양방향.repository;

import com.guide.jpa.OneToMany_양방향.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
