package com.api.learning.ElearningBE.security.impl;

import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.storage.entities.Account;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if (account == null){

            throw new NotFoundException("Email not found");
        }
        boolean enable = true;
        if (account.getStatus() != 1){
            log.error("Account is locked");
            enable = false;
        }
        Set<GrantedAuthority> grantedAuthorities = grantedAuthority(account);
        return new User(account.getEmail(), account.getPassword(), enable, true, true, true, grantedAuthorities);
    }

    private Set<GrantedAuthority> grantedAuthority(Account account){
        List<String> roles = new ArrayList<>();
        account.getGroup().getPermissions()
                .stream()
                .filter(permission -> permission.getPermissionCode() != null)
                .forEach(pName -> roles.add(pName.getPermissionCode()));
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.toUpperCase())).collect(Collectors.toSet());
    }
}
