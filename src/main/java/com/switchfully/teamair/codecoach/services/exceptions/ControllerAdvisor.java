package com.switchfully.teamair.codecoach.services.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public void handleIllegalArgumentException(IllegalArgumentException exception, HttpServletResponse response)
            throws IOException {
        logger.error("Illegal Argument: " + exception.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler({ EmailAlreadyExistsException.class })
    public ResponseEntity<Object> handleUserAlreadyExistsException(
        Exception ex, WebRequest request) {
        logger.error("User already exists");
        return new ResponseEntity<>(
            "Hey! This user already exists!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ InvalidUserException.class })
    public ResponseEntity<Object> handleInvalidUserException(
            Exception ex, WebRequest request) {
        logger.error("Not a valid User");
        return new ResponseEntity<>(
                "Hey! This user is not valid!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ InvalidTopicException.class })
    public ResponseEntity<Object> handleInvalidTopicException(
            Exception ex, WebRequest request) {
        logger.error("Not a valid Topic");
        return new ResponseEntity<>(
                "Hey! This topic is not valid!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ InvalidSessionException.class })
    public ResponseEntity<Object> handleInvalidSessionException(
            Exception ex, WebRequest request) {
        logger.error("Not a valid Session");
        return new ResponseEntity<>(
                "Hey! This session is not valid!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({ InvalidTokenException.class })
    public ResponseEntity<Object> handleInvalidTokenException(
            Exception ex, WebRequest request) {
        logger.error("Not a valid token");
        return new ResponseEntity<>(
                "Hey! This token is not valid!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({ InvalidSessionStatusException.class })
    public ResponseEntity<Object> handleInvalidSessionStatusException(
            Exception ex, WebRequest request) {
        logger.error("Not a valid session status");
        return new ResponseEntity<>(
                "Hey! This session status is not valid!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ InvalidTopicAssociationException.class })
    public ResponseEntity<Object> handleInvalidTopicAssociationException(
            Exception ex, WebRequest request) {
        logger.error("Not a valid Topic association");
        return new ResponseEntity<>(
                "Hey! This topic association is not valid!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({ CoachAllreadyContainsTopicException.class })
    public ResponseEntity<Object> handleCoachAllreadyContainsTopicException(
            Exception ex, WebRequest request) {
        logger.error("Coach allready contains this topic");
        return new ResponseEntity<>(
                "Hey! Coach allready contains this topic!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }



}
