package com.guide.event.domain.user;

import com.guide.event.global.config.event.PublishEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService userService;

    @Test
    public void 테스트() {

        test2();
//        repository.save(saveUser);
//        userService.createUser(saveUser);
    }

    @Transactional
    public void test2() {
        User user = User.builder()
                .uid("uchupura")
                .password("jang3827!")
                .name("장지현")
                .role(Role.ROLE_MANAGER)
                .build();
        repository.save(user);
    }
}