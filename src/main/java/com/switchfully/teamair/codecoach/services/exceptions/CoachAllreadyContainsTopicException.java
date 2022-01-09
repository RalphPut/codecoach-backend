package com.switchfully.teamair.codecoach.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CoachAllreadyContainsTopicException extends ResponseStatusException {

    private static final String DEFAULT_MESSAGE  = "This coach allready contains this topic";

    public CoachAllreadyContainsTopicException() {
        super(HttpStatus.BAD_REQUEST ,DEFAULT_MESSAGE);
    }
}

