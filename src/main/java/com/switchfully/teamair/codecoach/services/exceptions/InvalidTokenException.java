package com.switchfully.teamair.codecoach.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTokenException extends ResponseStatusException {

    private static final String DEFAULT_MESSAGE  = "Not a valid token.";

    public InvalidTokenException() {
        super(HttpStatus.BAD_REQUEST,DEFAULT_MESSAGE);
    }
}
