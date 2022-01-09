package com.switchfully.teamair.codecoach.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidSessionStatusException extends ResponseStatusException {

    private static final String DEFAULT_MESSAGE = "This SessionStatus is not valid.";

    public InvalidSessionStatusException() {
        super(HttpStatus.BAD_REQUEST, DEFAULT_MESSAGE);
    }
}
