package com.guide.jpa.OneToMany_양방향_cascade.repository;

import com.guide.jpa.OneToMany_양방향_cascade.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
