package com.guide.jpa.식별관계.repository;

import com.guide.jpa.식별관계.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
