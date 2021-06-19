package com.guide.aop.service;

import com.guide.aop.domain.Board;
import com.guide.aop.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService extends BoardPerformance {

    private final BoardRepository repository;

    public List<Board> getBoards() {
        return repository.findAll();
    }

    @Override
    public List<Board> findAll() {
        return repository.findAll();
    }
}
