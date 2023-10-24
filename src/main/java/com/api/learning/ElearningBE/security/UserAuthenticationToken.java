package com.api.learning.ElearningBE.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

public class UserAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final Object principal;
    private Object credentials;
    private final Object verifyCredentials;
    public UserAuthenticationToken(Object principal, Object credentials, Object verifyCredentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.verifyCredentials = verifyCredentials;
        setAuthenticated(false);
    }
    public UserAuthenticationToken(Object principal, Object credentials,
                                   Collection<? extends GrantedAuthority> authorities, Object verifyCredentials) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.verifyCredentials = verifyCredentials;
        super.setAuthenticated(true); // must use super, as we override
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public Object getVerifyCredentials() {
        return this.verifyCredentials;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }
}
