package com.guide.aop.service;

import com.guide.aop.domain.User;

import java.util.List;

public abstract class UserPerformance {
    private long before() {
        return System.currentTimeMillis();
    }

    private void after(long start) {
        long end = System.currentTimeMillis();
        System.out.println("수행 시간 : "+ (end - start));
    }

    public List<User> getUsers() {
        long start = before();
        List<User> users = findAll(); //구현은 자식 클래스에게 맡김
        after(start);

        return users;
    }
    public abstract List<User> findAll();
}
