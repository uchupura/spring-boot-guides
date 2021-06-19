package com.guide.aop.service;

import com.guide.aop.domain.Board;
import com.guide.aop.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository repository;

    @Description("보드 리스트 정보를 검색")
    public List<Board> getBoards() {
        return repository.findAll();
    }

    /*public List<Board> findAll() {
        return repository.findAll();
    }*/
}
