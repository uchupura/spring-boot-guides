package com.guide.aop.service;

import com.guide.aop.domain.User;
import com.guide.aop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> getUsers() {
        return repository.findAll();
    }

    @Transactional
    public User update(User user) {
        User saveUser = repository.save(user);
        return saveUser;
    }
    /*public List<User> findAll() {
        return repository.findAll();
    }*/
    @Transactional
    public void delete(Long id) {
        throw new RuntimeException("관리자만 삭제 가능합니다.");
    }
}
