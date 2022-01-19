package com.switchfully.teamair.codecoach.services;

import com.switchfully.teamair.codecoach.api.controllers.SessionController;
import com.switchfully.teamair.codecoach.api.dtos.FeedbackDtoRequest;
import com.switchfully.teamair.codecoach.domain.entities.Feedback;
import com.switchfully.teamair.codecoach.domain.entities.Session;
import com.switchfully.teamair.codecoach.domain.repositories.FeedbackRepository;
import com.switchfully.teamair.codecoach.domain.repositories.SessionRepository;
import com.switchfully.teamair.codecoach.services.exceptions.InvalidTokenException;
import com.switchfully.teamair.codecoach.services.utils.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class FeedbackService {

    private final Logger logger = LoggerFactory.getLogger(FeedbackService.class);

    private final SessionRepository sessionRepository;
    private final ValidationService validationService;
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(SessionRepository sessionRepository, ValidationService validationService, FeedbackRepository feedbackRepository) {
        this.sessionRepository = sessionRepository;
        this.validationService = validationService;
        this.feedbackRepository = feedbackRepository;
    }

    public void addFeedback(String sessionId, FeedbackDtoRequest feedbackDtoRequest, String authorizationToken){
        validationService.assertSessionExists(sessionId);
        Session session = sessionRepository.findSessionById(UUID.fromString(sessionId));
        validationService.assertSessionIsFinished(session);
        UUID userIdFromToken = validationService.getValidUserIdOrThrowBadRequest(authorizationToken);
        UUID coachId = session.getCoach().getUserId();
        UUID coacheeId = session.getCoachee().getUserId();
        int feedbackId = session.getFeedback().getFeedbackId();
        Feedback feedback = feedbackRepository.findFeedbackByFeedbackId(feedbackId);
        if (coachId.equals(userIdFromToken)) {
            logger.info(String.format("Adding feedback from coach with feedbackId %s", feedbackId));
            feedback.setFeedbackFromCoach(feedbackDtoRequest.getFeedback());
        } else if (coacheeId.equals(userIdFromToken)) {
            logger.info(String.format("Adding feedback from coachee with feedbackId %s", feedbackId));
            feedback.setFeedbackFromCoachee(feedbackDtoRequest.getFeedback());
        } else {
            throw new InvalidTokenException();
        }
    }
}
