package com.guide.oauth2.service;

import com.guide.oauth2.config.exception.NotFoundException;
import com.guide.oauth2.dto.Filter;
import com.guide.oauth2.dto.UserDto;
import com.guide.oauth2.entity.user.User;
import com.guide.oauth2.repository.UserRepository;
import com.guide.oauth2.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService  implements BaseService<UserDto, Long> {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    @Override
    public List<UserDto> readAll() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(m -> {
                    return UserMapper.INSTANCE.map(m);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDto> search(Pageable pageable) {
        Page<User> members = repository.findAll(pageable);
        return members.map(m -> {
            return UserMapper.INSTANCE.map(m);
        });
    }

    @Transactional
    @Override
    public UserDto create(UserDto dto) {
        dto.encryptPassword(passwordEncoder.encode(dto.getPassword()));
        User entity = UserMapper.INSTANCE.map(dto);
        User user = repository.save(entity);
        return UserMapper.INSTANCE.map(user);
    }

    @Override
    public UserDto read(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("user"));
        return UserMapper.INSTANCE.map(user);
    }

    @Transactional
    @Override
    public UserDto update(Long id, UserDto dto) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("member"));
        user.update(dto.getName(), dto.getPhone(), dto.getEmail());
        return UserMapper.INSTANCE.map(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<UserDto> search(Filter filter, Pageable pageable) {

        Page<User> users = repository.findByRole(filter.getRole(), pageable);
        return users.map(m -> {
            return UserMapper.INSTANCE.map(m);
        });
    }
}
