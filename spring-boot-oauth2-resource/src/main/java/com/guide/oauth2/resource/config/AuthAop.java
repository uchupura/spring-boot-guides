package com.guide.oauth2.resource.config;

import com.guide.oauth2.resource.exception.BusinessException;
import com.guide.oauth2.resource.exception.ExceptionType;
import com.guide.oauth2.resource.model.User;
import com.guide.oauth2.resource.repository.UserRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;

import static org.springframework.util.ObjectUtils.isEmpty;

@Aspect
@Component
public class AuthAop {
    @Autowired
    private UserRepository userRepository;

    @Around("execution(* *(.., @AuthUser (*), ..))")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object[] args = Arrays.stream(joinPoint.getArgs()).map(o -> {
            if (o instanceof User && !isEmpty(authentication) && !isEmpty(authentication.getPrincipal())) {
                o = userRepository.findByUid((String)authentication.getPrincipal()).orElseThrow(() -> new BusinessException(ExceptionType.INVALID_PARAM));
            }
            return o;
        }).toArray();
        return joinPoint.proceed(args);
    }
}
