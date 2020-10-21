package com.guide.oauth2.config.jpa;

import com.guide.oauth2.config.security.PrincipalUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringAuditorAware implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {

        PrincipalUserDetails userDetails = (PrincipalUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(userDetails.getUserId());
    }
}
