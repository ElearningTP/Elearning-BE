package com.api.learning.ElearningBE.security;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.security.impl.UserDetailsImpl;
import com.api.learning.ElearningBE.security.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) authentication;
        String email = userAuthenticationToken.getName();
        String password = userAuthenticationToken.getCredentials() == null ? null : authentication.getCredentials().toString();
        boolean verifyCredentials = Boolean.parseBoolean(userAuthenticationToken.getVerifyCredentials().toString());
        System.out.println("Provider: "+ userAuthenticationToken.getUserKind());
        Integer userKind = userAuthenticationToken.getUserKind();
        UserDetailsImpl userDetails;
        if (userKind.equals(ELearningConstant.ROLE_KIND_STUDENT)){
            userDetails = userDetailsService.loadUserByUsername(email);
        }else {
            if (userKind.equals(ELearningConstant.ROLE_KIND_TEACHER)) {
                userDetails = userDetailsService.loadTeacherByEmail(email);
            }else{
                throw new InvalidException("Invalid user kind");
            }
        }
//        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(email);
        if (userDetails.getStatus().equals(ELearningConstant.ACCOUNT_STATUS_LOCKED)){
            throw new BadCredentialsException("Account has been locked, please contact the administrator");
        }
        if (verifyCredentials){
            assert password != null;
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(password, userDetails.getPassword())){
                return new UserAuthenticationToken(email, password, userDetails.getAuthorities(), true, userDetails.getUserKind());
            }else {
                throw new BadCredentialsException("Incorrect password, please enter again");
            }
        }else {
            return new UserAuthenticationToken(email, "N/A", userDetails.getAuthorities(), false, userDetails.getUserKind());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UserAuthenticationToken.class);
//        return UserAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
