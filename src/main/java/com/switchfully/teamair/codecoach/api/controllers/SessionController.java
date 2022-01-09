package com.switchfully.teamair.codecoach.api.controllers;

import com.switchfully.teamair.codecoach.api.dtos.SessionDtoRequest;
import com.switchfully.teamair.codecoach.api.dtos.SessionDtoResponse;
import com.switchfully.teamair.codecoach.services.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessions")
public class SessionController {


    private final Logger logger = LoggerFactory.getLogger(SessionController.class);

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping(path = "/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('COACHEE')")
    public SessionDtoResponse getSessionById(@PathVariable String sessionId, @RequestHeader String authorization) {
        logger.info("Getting session by id {}", sessionId);
        return sessionService.getSessionById(sessionId, authorization);
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('COACHEE')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSession(@RequestBody SessionDtoRequest sessionDtoRequest, @RequestHeader String authorization) {
        logger.info("Creating session");
        sessionService.requestSession(sessionDtoRequest, authorization);
    }

    @PutMapping(path = "/{sessionId}")
    public void updateSession(@PathVariable String sessionId, @RequestBody String sessionStatus, @RequestHeader String authorization) {
        sessionService.updateSessionStatus(sessionId, sessionStatus, authorization);
    }

    @DeleteMapping
    public void deleteSession(SessionDtoRequest sessionDtoRequest) {
        sessionService.delete(sessionDtoRequest);
    }


}
