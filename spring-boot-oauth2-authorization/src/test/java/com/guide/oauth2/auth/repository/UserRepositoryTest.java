package com.guide.oauth2.auth.repository;

import com.guide.oauth2.auth.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.Assert.*;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void 유저_등록() {
        User user = new User("uchupura", passwordEncoder.encode("jang3827"), "장지현", Collections.singletonList("ROLE_USER"));
        userRepository.save(user);
    }
}