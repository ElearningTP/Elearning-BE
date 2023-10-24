package com.api.learning.ElearningBE.exceptions;

import javax.security.sasl.AuthenticationException;

public class AccountAuthenticationException extends AuthenticationException {
    public AccountAuthenticationException(String message) {
        super(message);
    }
}
