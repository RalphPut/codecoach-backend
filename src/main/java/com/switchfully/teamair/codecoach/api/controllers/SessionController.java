package com.switchfully.teamair.codecoach.api.controllers;

import com.switchfully.teamair.codecoach.api.dtos.FeedbackDtoRequest;
import com.switchfully.teamair.codecoach.api.dtos.SessionDtoRequest;
import com.switchfully.teamair.codecoach.api.dtos.SessionDtoResponse;
import com.switchfully.teamair.codecoach.domain.entities.Feedback;
import com.switchfully.teamair.codecoach.services.FeedbackService;
import com.switchfully.teamair.codecoach.services.SessionService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessions")
@CrossOrigin(origins = "https://codecoach-colruyt.netlify.app")
//@CrossOrigin(origins = "http://localhost:4200")
public class SessionController {


    private final Logger logger = LoggerFactory.getLogger(SessionController.class);

    private final SessionService sessionService;
    private final FeedbackService feedbackService;

    public SessionController(SessionService sessionService, FeedbackService feedbackService) {
        this.sessionService = sessionService;
        this.feedbackService = feedbackService;
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

    @PutMapping(path = "/{sessionId}/addfeedback")
    @PreAuthorize("hasAuthority('COACHEE')")
    @ResponseStatus(HttpStatus.OK)
    public void addFeedback(@PathVariable String sessionId, @RequestBody String feedbackDtoRequest, @RequestHeader String authorization) {
        logger.info("Addfeedback: Getting session by id {}", sessionId);
        feedbackService.addFeedback(sessionId, feedbackDtoRequest, authorization);
    }

    @PutMapping(path = "/{sessionId}")
    @PreAuthorize("hasAuthority('COACHEE')")
    @ResponseStatus(HttpStatus.OK)
    public void updateSession(@PathVariable String sessionId, @RequestBody String sessionStatus, @RequestHeader String authorization) {
        logger.info(sessionStatus);
        sessionService.updateSessionStatus(sessionId, sessionStatus, authorization);

    }

   // @GetMapping(path = "/{sessionId}/getfeedback")
   // @ResponseStatus(HttpStatus.OK)
   // @PreAuthorize("hasAuthority('COACHEE')")
   // public Feedback getFeedback(@PathVariable String sessionId, @RequestHeader String authorization){
    //    logger.info("Getting feedback for {)",sessionId);
    //    return feedbackService.getFeedback(sessionId,authorization);
   // }


    @DeleteMapping
    public void deleteSession(SessionDtoRequest sessionDtoRequest) {
        sessionService.delete(sessionDtoRequest);
    }


}
