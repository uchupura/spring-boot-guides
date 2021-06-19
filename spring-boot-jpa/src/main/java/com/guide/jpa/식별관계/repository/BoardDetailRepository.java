package com.guide.jpa.식별관계.repository;

import com.guide.jpa.식별관계.entity.BoardDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardDetailRepository extends JpaRepository<BoardDetail, Long> {
}
