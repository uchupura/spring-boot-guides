package com.guide.oauth2.resource.service;

import com.guide.oauth2.resource.model.User;
import com.guide.oauth2.resource.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository repository;

    public User getUserByUid(String uid) {
        return repository.findByUid(uid).orElseThrow(() -> new RuntimeException("user not found"));
    }

    @Transactional
    public User createUser(User user) {
        return repository.save(user);
    }

    @Transactional
    public User updateUser(User user) {
        User update = getUserByUid(user.getUid());
        update.changePassword(user.getPassword());
        return update;
    }

    @Secured({"ROLE_MANAGER"})
    public List<User> findAll() {
        return repository.findAll();
    }
}