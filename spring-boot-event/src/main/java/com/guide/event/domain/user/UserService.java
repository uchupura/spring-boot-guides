package com.guide.event.domain.user;

import com.guide.event.global.config.event.PublishEvent;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository repository;

    @PublishEvent
    public User createUser(User user) {
        user.changePassword("jang3827!!!");
        return user;
        /*User saveUser = User.builder()
                .uid(user.getUid())
                .password(user.getPassword())
                .name(user.getName())
                .role(user.getRole())
                .build();
        return repository.save(saveUser);*/
    }

    @Transactional
    public User updateUser(User user) {
        User savedUser = repository.findById(user.getId()).orElseThrow(() -> new RuntimeException());
        savedUser.changePassword(user.getPassword());
        return savedUser;
    }
}