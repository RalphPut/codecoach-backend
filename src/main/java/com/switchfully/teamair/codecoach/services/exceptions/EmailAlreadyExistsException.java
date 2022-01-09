package com.switchfully.teamair.codecoach.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailAlreadyExistsException extends ResponseStatusException {

    private static final String DEFAULT_MESSAGE  = "This email address already exists";

    public EmailAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST ,DEFAULT_MESSAGE);
    }
}
