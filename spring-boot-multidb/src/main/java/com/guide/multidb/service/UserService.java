package com.guide.multidb.service;

import com.guide.multidb.entity.user.User;
import com.guide.multidb.repository.user.UserRepository;
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

    public User get(Long id) {
        return repository.findById(id).orElse(null);
    }
    @Transactional
    public void create(User user) {
        repository.save(user);
    }

    @Transactional
    public void update(Long id) {
        User user = repository.findById(id).orElse(null);
        user.setName("modified");
    }
}