package com.guide.oauth2.resource.security;

import org.junit.platform.commons.util.AnnotationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MockUserUtils {
    private MockUserUtils() {
        throw new InstantiationError();
    }

    public static SecurityContext getSecurityContext(WithMockUser withUser) {
        String username = StringUtils.hasLength(withUser.username()) ? withUser
                .username() : withUser.value();
        if (username == null) {
            throw new IllegalArgumentException(withUser
                    + " cannot have null username on both username and value properties");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : withUser.authorities()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }

        if (grantedAuthorities.isEmpty()) {
            for (String role : withUser.roles()) {
                if (role.startsWith("ROLE_")) {
                    throw new IllegalArgumentException("roles cannot start with ROLE_ Got "
                            + role);
                }
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        } else if (!(withUser.roles().length == 1 && "USER".equals(withUser.roles()[0]))) {
            throw new IllegalStateException("You cannot define roles attribute "+ Arrays.asList(withUser.roles())+" with authorities attribute "+ Arrays.asList(withUser.authorities()));
        }

        Object principal = username;
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal, withUser.password(), grantedAuthorities);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }

    public static Authentication getAuthentication(WithMockUser withMockUser) {
        SecurityContext securityContext = getSecurityContext(withMockUser);
        return securityContext.getAuthentication();
    }

    private static WithSecurityContextFactory<? extends Annotation> createFactory(
            WithSecurityContext withSecurityContext) {
        Class<? extends WithSecurityContextFactory<? extends Annotation>> clazz = withSecurityContext
                .factory();
        try {
            return BeanUtils.instantiateClass(clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * User authentication creation
     *
     * @param user
     * @return an Authentication with provided user details
     */
    public static UsernamePasswordAuthenticationToken createUserAuthentication(final WithMockUser user) {
        final String principal = user.username().isEmpty() ? user.value() : user.username();

        final Stream<String> grants = user.authorities().length == 0 ?
                Stream.of(user.roles()).map(r -> "ROLE_" + r) :
                Stream.of(user.authorities());

        final Set<? extends GrantedAuthority> userAuthorities = grants
                .map(auth -> new SimpleGrantedAuthority(auth))
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(
                new User(principal, user.password(), userAuthorities),
                principal + ":" + user.password(),
                userAuthorities);
    }
}
