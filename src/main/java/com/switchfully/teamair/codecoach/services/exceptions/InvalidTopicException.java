package com.switchfully.teamair.codecoach.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTopicException extends ResponseStatusException {

    private static final String DEFAULT_MESSAGE  = "This topic is not valid.";

    public InvalidTopicException() {
        super(HttpStatus.BAD_REQUEST,DEFAULT_MESSAGE);
    }
}
