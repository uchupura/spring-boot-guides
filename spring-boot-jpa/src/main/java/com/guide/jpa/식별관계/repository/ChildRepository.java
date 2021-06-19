package com.guide.jpa.식별관계.repository;

import com.guide.jpa.식별관계.entity.Child;
import com.guide.jpa.식별관계.entity.ChildId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, ChildId> {
}
