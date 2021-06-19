package com.guide.aop.service;

import com.guide.aop.domain.Board;

import java.util.List;

public abstract class BoardPerformance {
    private long before() {
        return System.currentTimeMillis();
    }

    private void after(long start) {
        long end = System.currentTimeMillis();
        System.out.println("수행 시간 : "+ (end - start));
    }

    public List<Board> getBoards() {
        long start = before();
        List<Board> boards = findAll(); //구현은 자식 클래스에게 맡김
        after(start);

        return boards;
    }
    public abstract List<Board> findAll();
}
