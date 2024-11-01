package net.loove.auth.exceptions;


import org.springframework.security.core.AuthenticationException;

public class GoogleUserAlreadyRegistered extends AuthenticationException {
    public GoogleUserAlreadyRegistered() {
        super("User already registered");
    }
}
