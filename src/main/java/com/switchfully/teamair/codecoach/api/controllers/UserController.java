package com.switchfully.teamair.codecoach.api.controllers;

import com.switchfully.teamair.codecoach.api.dtos.*;
import com.switchfully.teamair.codecoach.services.SessionService;
import com.switchfully.teamair.codecoach.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://codecoach-colruyt.netlify.app")
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid UserDtoRequest userDtoRequest) {
        logger.info("Controller: Creating user");
        userService.registerUser(userDtoRequest);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAuthority('COACHEE')")
    public List<UserDtoResponse> getAllCoaches(@RequestParam(required = false) String topic) {
        logger.info("Controller: Getting all coaches");
        return userService.getAllCoaches(topic);
    }

    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAuthority('COACHEE')")
    public UserDtoResponse getUserById(@PathVariable String userId,
                                       @RequestHeader String authorization) {
        logger.info("Controller: Getting user with userId {}", userId);
        return userService.getUserById(userId, authorization);
    }

    @PutMapping(path = "/{userId}", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAuthority('COACHEE')")
    public String updateUser(@PathVariable String userId,
                             @RequestBody(required = false)
                             @Valid UpdateUserDtoRequest updateUserDtoRequest,
                             @RequestParam(required = false) boolean coachStatus,
                             @RequestHeader String authorization) {
        logger.info("Controller: Updating user with userId {}", userId);
        String token = "";
        if (coachStatus) {
            token = userService.toggleCoachStatus(userId, authorization);
        }
        userService.updateUser(userId, updateUserDtoRequest, authorization);
        return token;
    }

    @GetMapping(path = "{userId}/sessions")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('COACHEE')")
    public List<SessionDtoResponse> getAllSessionsPerUser(@PathVariable String userId,
                                                          @RequestHeader String authorization) {

        return sessionService.getAllSessionsById(userId, authorization);
    }

    //  @PostMapping(path = "{userId}/topics", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//  @ResponseStatus(value = HttpStatus.OK)
//  @PreAuthorize("hasAuthority('COACH')")
//  public void updateCoachTopics(@PathVariable String userId,
//                                @RequestBody @Valid TopicAssociationDtoRequest topicAssociationDtoRequest,
//                                @RequestHeader String authorization) {
//    logger.info("Controller: Updating coach topics with userId {}", userId);
//
//    userService.updateTopicAssociation(userId, topicAssociationDtoRequest);
//  }
//
//  @PutMapping(path = "{userId}/topics", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//  @ResponseStatus(value = HttpStatus.OK)
//  @PreAuthorize("hasAuthority('COACH')")
//  public void updateCoachKnowledgelevel(@PathVariable String userId,
//                                @RequestBody TopicAssociationDtoRequest topicAssociationDtoRequest) {
//    logger.info("Controller: Updating coachknowledgelevel with userId {}", userId);
//
//    userService.editTopicKnowledgelevel(userId, topicAssociationDtoRequest);
//  }

}
