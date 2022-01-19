package com.switchfully.teamair.codecoach.api.controllers;

import com.switchfully.teamair.codecoach.api.dtos.TopicDtoResponse;
import com.switchfully.teamair.codecoach.api.dtos.UserDtoResponse;
import com.switchfully.teamair.codecoach.services.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
//@CrossOrigin(origins = "https://codecoach-colruyt.netlify.app")
@CrossOrigin(origins = "http://localhost:4200")
public class TopicController {

    private final Logger logger = LoggerFactory.getLogger(TopicController.class);

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAuthority('COACHEE')")
    public List<TopicDtoResponse> getAllTopics() {
        logger.info("Controller: Getting all topics");
        return topicService.getAllTopics();
    }
}
