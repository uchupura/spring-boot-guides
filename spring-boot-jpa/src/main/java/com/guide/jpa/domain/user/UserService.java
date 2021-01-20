package com.guide.jpa.domain.user;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;

    public boolean exist(User user) {
        User duplicatedUser = userRepository.findByName(user.getName());
        return duplicatedUser != null;
    }
}