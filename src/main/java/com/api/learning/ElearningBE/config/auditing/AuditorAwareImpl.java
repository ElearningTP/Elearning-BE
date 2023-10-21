package com.api.learning.ElearningBE.config.auditing;

import com.api.learning.ElearningBE.security.impl.UserDetailsImpl;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    public Optional<String> getCurrentAuditor(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()){
            return Optional.of("anonymous");
        }else {
            return Optional.of(authentication.getName());
        }
    }
}
