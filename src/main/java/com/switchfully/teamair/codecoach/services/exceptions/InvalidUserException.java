package com.switchfully.teamair.codecoach.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidUserException extends ResponseStatusException {

    private static final String DEFAULT_MESSAGE  = "This user is not valid.";

    public InvalidUserException() {
        super(HttpStatus.BAD_REQUEST,DEFAULT_MESSAGE);
    }

}
