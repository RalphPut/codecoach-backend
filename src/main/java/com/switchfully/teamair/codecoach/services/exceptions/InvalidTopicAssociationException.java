package com.switchfully.teamair.codecoach.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTopicAssociationException extends ResponseStatusException {

    private static final String DEFAULT_MESSAGE  = "This topic association is not valid.";

    public InvalidTopicAssociationException() {
        super(HttpStatus.BAD_REQUEST,DEFAULT_MESSAGE);
    }
}
