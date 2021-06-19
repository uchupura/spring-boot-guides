package com.guide.aop.service;

import com.guide.aop.domain.User;
import com.guide.aop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService extends UserPerformance {

    private final UserRepository repository;

    public List<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}
