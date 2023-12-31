package com.api.learning.ElearningBE.security.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private final Long accountId;
    private final Integer kind;
    private final String fullName;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Integer status;
    private final String avatar;
    private final String role;

    public UserDetailsImpl(Long accountId, Integer kind, String fullName, String email, String password, Collection<? extends GrantedAuthority> authorities, Integer status, String avatar, String role) {
        this.accountId = accountId;
        this.kind = kind;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.status = status;
        this.avatar = avatar;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }
    public String getEmail() {
        return email;
    }
    public Integer getStatus() {
        return status;
    }
    public String getAvatar() {
        return avatar;
    }
    public String getRole() {
        return role;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Integer getKind() {
        return kind;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
