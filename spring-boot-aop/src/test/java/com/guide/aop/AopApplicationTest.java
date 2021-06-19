package com.guide.aop;

import com.guide.aop.service.IBoardService;
import com.guide.aop.service.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AopApplicationTest {
    @Autowired
    private IBoardService boardService;

    @Autowired
    private IUserService userService;

    @Test
    void findBoards()  throws Exception {
        Assertions.assertEquals(boardService.getBoards().size(), 100);
    }

    @Test
    void findUsers() throws Exception {
        Assertions.assertEquals(userService.getUsers().size(), 100);
    }
}