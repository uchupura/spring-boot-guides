package com.guide.jpa.식별관계.repository;

import com.guide.jpa.식별관계.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Long> {
}
