package com.guide.oauth2.service;

import com.guide.oauth2.config.security.PrincipalUserDetails;
import com.guide.oauth2.entity.user.User;
import com.guide.oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        User user = repository.findByLoginId(loginId);
        if (user == null) {
            throw new UsernameNotFoundException(loginId);
        }

        return new PrincipalUserDetails(
                user.getId(),
                user.getLoginId(),
                user.getPassword(),
                user.getRole()
        );
    }
}
