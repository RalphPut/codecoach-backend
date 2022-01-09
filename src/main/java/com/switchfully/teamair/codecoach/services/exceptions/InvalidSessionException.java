package com.switchfully.teamair.codecoach.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidSessionException extends ResponseStatusException {

    private static final String DEFAULT_MESSAGE = "This Session is not valid.";

    public InvalidSessionException() {
        super(HttpStatus.BAD_REQUEST, DEFAULT_MESSAGE);
    }
}
