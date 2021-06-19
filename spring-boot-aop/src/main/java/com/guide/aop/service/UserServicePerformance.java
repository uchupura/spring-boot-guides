package com.guide.aop.service;

import com.guide.aop.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class UserServicePerformance implements IUserService {

    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userService;

    @Override
    public List<User> getUsers() {
        long start = before();
        List<User> users = userService.getUsers();
        after(start);
        return users;
    }

    private long before() {
        return System.currentTimeMillis();
    }

    private void after(long start) {
        long end = System.currentTimeMillis();
        System.out.println("수행 시간 : "+ (end - start));
    }
}
