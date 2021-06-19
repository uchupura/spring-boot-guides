package com.guide.aop;

import com.guide.aop.domain.History;
import com.guide.aop.domain.User;
import com.guide.aop.repository.HistoryRepository;
import com.guide.aop.service.BoardService;
import com.guide.aop.service.IBoardService;
import com.guide.aop.service.IUserService;
import com.guide.aop.service.UserService;
import com.guide.common.enumtype.ExceptionType;
import com.guide.common.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AopApplicationTest {
    @Autowired
    private BoardService boardService;
    @Autowired
    private UserService userService;
    @Autowired
    private HistoryRepository historyRepository;

    @Test
    void findBoards()  throws Exception {
        Assertions.assertEquals(boardService.getBoards().size(), 100);
    }

    @Test
    void findUsers() throws Exception {
        Assertions.assertEquals(userService.getUsers().size(), 100);
    }

    @Test
    public void updateUsers() throws Exception {
        List<User> users = userService.getUsers();
        for(int i=0;i<5;i++){
            User user = users.get(i);
            user.setEmail("jojoldu@gmail.com");
            userService.update(user);
        }

        List<History> histories = historyRepository.findAll();
        assertThat(histories.size()).isEqualTo(5);
        assertThat(histories.get(0).getUserIdx()).isEqualTo(1L);
        assertThat(histories.get(1).getUserIdx()).isEqualTo(2L);
    }

    @Test
    public void deleteUsers() throws Exception {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.delete(1l));
        System.out.println(exception.getMessage());
    }
}