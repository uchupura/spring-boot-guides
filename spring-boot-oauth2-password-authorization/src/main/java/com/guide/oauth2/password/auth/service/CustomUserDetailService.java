package com.guide.oauth2.password.auth.service;

import com.guide.oauth2.password.auth.config.CustomOauthException;
import com.guide.oauth2.password.auth.model.User;
import com.guide.oauth2.password.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        //@TODO 패스워드 만료 기한 체크
        User user = userRepository.findByUid(uid).orElseThrow(() -> new UsernameNotFoundException("user is not exists"));
        /*if (!isEmpty(user)) {
            throw new CustomOauthException("password expire");
        }*/
        detailsChecker.check(user);
        return user;
    }
}
