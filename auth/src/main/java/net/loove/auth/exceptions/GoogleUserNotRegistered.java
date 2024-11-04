package net.loove.auth.exceptions;


import org.springframework.security.core.AuthenticationException;

public class GoogleUserNotRegistered extends AuthenticationException {
    public GoogleUserNotRegistered() {
        super("Google user not registered");
    }
}
