package com.api.learning.ElearningBE.security.impl;

import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.storage.entities.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if (account == null){
            throw new NotFoundException(String.format("Email %s does not exist",email));
        }
        Set<GrantedAuthority> grantedAuthorities = grantedAuthority(account);
        return new UserDetailsImpl(account.getId(), account.getFullName(), account.getEmail(), account.getPassword(), grantedAuthorities, account.getStatus(), account.getAvatarPath(), account.getRole().getName(), account.getKind());
    }
    private Set<GrantedAuthority> grantedAuthority(Account account){
        List<String> roles = new ArrayList<>();
        account.getRole().getPermissions()
                .stream()
                .filter(permission -> permission.getPermissionCode() != null)
                .forEach(pName -> roles.add(pName.getPermissionCode()));
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.toUpperCase())).collect(Collectors.toSet());
    }
}
