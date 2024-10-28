package net.loove.profiles.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException() {
        super("Profile not found");
    }

    public ProfileNotFoundException(String message) {
        super(message);
    }

}
