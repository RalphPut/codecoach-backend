package com.switchfully.teamair.codecoach.services;

import com.switchfully.teamair.codecoach.api.dtos.SessionDtoRequest;
import com.switchfully.teamair.codecoach.api.dtos.SessionDtoResponse;
import com.switchfully.teamair.codecoach.domain.entities.*;
import com.switchfully.teamair.codecoach.domain.repositories.SessionRepository;
import com.switchfully.teamair.codecoach.domain.repositories.TopicRepository;
import com.switchfully.teamair.codecoach.domain.repositories.UserRepository;
import com.switchfully.teamair.codecoach.services.exceptions.InvalidSessionStatusException;
import com.switchfully.teamair.codecoach.services.mappers.SessionMapper;
import com.switchfully.teamair.codecoach.services.utils.ValidationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final ValidationService validationService;
    private final MessagingService messagingService;

    private final Logger logger = LoggerFactory.getLogger(SessionService.class);

    public SessionService(SessionRepository sessionRepository, SessionMapper sessionMapper, UserRepository userRepository, TopicRepository topicRepository, ValidationService validationService, MessagingService messagingService) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.validationService = validationService;
        this.messagingService = messagingService;
        updateSessionStatusToDoneWaitingForFeedback();
    }

    public void requestSession(SessionDtoRequest sessionDtoRequest, String authorizationToken) {
        validationService.assertCreateSessionNotNull(sessionDtoRequest);
        String userId = sessionDtoRequest.getCoacheeId();
        validationService.assertValidToken(userId, authorizationToken);

        User coach = userRepository.findUserByUserId(UUID.fromString(sessionDtoRequest.getCoachId()));
        User coachee = userRepository.findUserByUserId(UUID.fromString(sessionDtoRequest.getCoacheeId()));
        Topic topic = topicRepository.findTopicByName(sessionDtoRequest.getTopic());

        validationService.assertUserExists(coach.getUserId());
        validationService.assertUserExists(coachee.getUserId());
        validationService.assertUserIsACoach(coach);
        validationService.assertTopicExists(topic.getId());
//    validationService.assertTopicBelongsToACoach(coach, topic);
        Session session = sessionMapper.toEntity(sessionDtoRequest);
        session.setFeedback(new Feedback());
           String message = messagingService.createRequestSessionMessage(session);
          messagingService.sendMessageToTopic(message);
        sessionRepository.save(session);

    }

    public List<SessionDtoResponse> getAllSessionsById(String userId, String authorizationToken) {
        validationService.assertValidToken(userId, authorizationToken);
        List<SessionDtoResponse> sessions = new ArrayList<>();
        List<SessionDtoResponse> coacheeSessions = getCoacheeSessionsById(userId);
        List<SessionDtoResponse> coachSessions = getCoachSessionsById(userId);
        sessions.addAll(coachSessions);
        sessions.addAll(coacheeSessions);
        return sessions;
    }

    private List<SessionDtoResponse> getCoacheeSessionsById(String coacheeId) {
        return sessionRepository.findAll().stream()
                .filter(session -> session.getCoachee().getUserId().equals(UUID.fromString(coacheeId)))
                .map(sessionMapper::toResponse)
                .toList();
    }

    private List<SessionDtoResponse> getCoachSessionsById(String coachId) {
        return sessionRepository.findAll().stream()
                .filter(session -> session.getCoach().getUserId().equals(UUID.fromString(coachId)))
                .map(sessionMapper::toResponse)
                .toList();
    }


    private void updateSessionStatusToDoneWaitingForFeedback() {
        logger.info("Updating the SessionStatus");
        for (Session session : sessionRepository.findAll()) {
            if (SessionStatus.ACCEPTED.equals(session.getSessionStatus()) && session.getDateTime().isBefore(LocalDateTime.now())) {
                session.setSessionStatus(SessionStatus.DONE_WAITING_FOR_FEEDBACK);
                sessionRepository.save(session);
            } else if(SessionStatus.REQUESTED.equals(session.getSessionStatus()) && session.getDateTime().isBefore(LocalDateTime.now())){
                session.setSessionStatus(SessionStatus.DECLINED);
                sessionRepository.save(session);
            }
        }
    }


    public SessionDtoResponse getSessionById(String sessionId, String authorizationToken) {
        validationService.assertSessionExists(sessionId);
        Session session = sessionRepository.findSessionById(UUID.fromString(sessionId));
        UUID token = validationService.getValidUserIdOrThrowBadRequest(authorizationToken);
        validationService.assertUserIdIsPartOfSessionAsCoachOrCoachee(token, session);
        return sessionMapper.toResponse(session);
    }

    public void updateSessionStatus(String sessionId, String newStatus, String authorizationToken) {
        validationService.assertSessionExists(sessionId);
        Session session = sessionRepository.findSessionById(UUID.fromString(sessionId));
        UUID token = validationService.getValidUserIdOrThrowBadRequest(authorizationToken);
        String roleStatus = validationService.assertUserIdIsPartOfSessionAsCoachOrCoachee(token, session);
        SessionStatus currentSessionStatus = session.getSessionStatus();
        if (roleStatus.equals("COACH")) {
            if (currentSessionStatus.equals(SessionStatus.REQUESTED) && (newStatus.equals("ACCEPTED") || newStatus.equals("DECLINED"))) {
                session.setSessionStatus(SessionStatus.valueOf(newStatus));
            } else if (currentSessionStatus.equals(SessionStatus.ACCEPTED) && newStatus.equals("CANCELLED_BY_COACH")) {
                session.setSessionStatus(SessionStatus.valueOf(newStatus));
                String message = messagingService.createCancelSessionMessageByCoach(session);
                messagingService.sendMessageToTopic(message);

            } else {
                throw new InvalidSessionStatusException();
            }
        } else {
            if (currentSessionStatus.equals(SessionStatus.REQUESTED) || (currentSessionStatus.equals(SessionStatus.ACCEPTED))
                    && newStatus.equals("CANCELLED_BY_COACHEE")) {
                session.setSessionStatus(SessionStatus.valueOf(newStatus));
                String message = messagingService.createCancelSessionMessageByCoachee(session);
                messagingService.sendMessageToTopic(message);
            } else {
                throw new InvalidSessionStatusException();
            }
        }
    }

    public void delete(SessionDtoRequest sessionDtoRequest) {
        sessionRepository.delete(sessionMapper.toEntity(sessionDtoRequest));
    }
}
