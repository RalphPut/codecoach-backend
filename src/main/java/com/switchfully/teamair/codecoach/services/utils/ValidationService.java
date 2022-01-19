package com.switchfully.teamair.codecoach.services.utils;

import com.switchfully.teamair.codecoach.api.dtos.SessionDtoRequest;
import com.switchfully.teamair.codecoach.api.dtos.TopicAssociationDtoRequest;
import com.switchfully.teamair.codecoach.api.dtos.UserDtoRequest;
import com.switchfully.teamair.codecoach.domain.entities.*;
import com.switchfully.teamair.codecoach.domain.repositories.RoleRepository;
import com.switchfully.teamair.codecoach.domain.repositories.SessionRepository;
import com.switchfully.teamair.codecoach.domain.repositories.TopicRepository;
import com.switchfully.teamair.codecoach.domain.repositories.UserRepository;
import com.switchfully.teamair.codecoach.services.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class ValidationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TopicRepository topicRepository;
    private final SessionRepository sessionRepository;
    private final Logger logger = LoggerFactory.getLogger(ValidationService.class);

    public ValidationService(UserRepository userRepository, RoleRepository roleRepository, TopicRepository topicRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.topicRepository = topicRepository;
        this.sessionRepository = sessionRepository;
    }

    public void assertEmailIsNew(String email) {
        if (userRepository.findByEmail(email) != null) {
            logger.info("Email already exists");
            throw new EmailAlreadyExistsException();
        }
    }

    public void assertCreateUserNotNull(UserDtoRequest userDtoRequest) {
        if (userDtoRequest == null) {
            logger.info("Not a valid User object");
            throw new InvalidUserException();
        }
    }

    public void assertCreateSessionNotNull(SessionDtoRequest sessionDtoRequest) {
        if (sessionDtoRequest == null) {
            logger.info("Not a valid User object");
            throw new InvalidSessionException();
        }
    }

    public void assertTopicAssociationDtoRequestNotNull(TopicAssociationDtoRequest topicAssociationDtoRequest) {
        if (topicAssociationDtoRequest == null) {
            logger.info("Not a valid TopicAssociation");
            throw new InvalidTopicAssociationException();
        }
    }

    public void assertUserExists(UUID userId) {
        if (userRepository.findUserByUserId(userId) == null) {
            logger.info("User does not exist");
            throw new InvalidUserException();
        }
    }

    public void assertTopicExists(int topicId) {
        if (topicRepository.findTopicById(topicId) == null) {
            logger.info("Topic does not exist");
            throw new InvalidTopicException();
        }
    }

    public void assertSessionExists(String sessionId) {
        if (sessionRepository.findSessionById(UUID.fromString(sessionId)) == null) {
            logger.info("Session does not exist");
            throw new InvalidSessionException();
        }
    }

    public void assertUserIsACoach(User user) {
        if (!user.getRoles().contains(roleRepository.findRoleById(2))) {
            logger.info("This user is not a coach");
            throw new InvalidUserException();
        }
    }

    public void assertPasswordIsValid(String password) {
        if (password.length() < 6) {
            logger.info("Password too short");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 6 characters long");
        }
    }

    public void assertSessionIsFinished(Session session){
        if(!SessionStatus.FINISHED.equals(session.getSessionStatus())){
            throw new SessionNotFinishedException();
        }
    }

    public void assertTopicBelongsToACoach(User coach, Topic topic) {
        List<Topic> topics = coach.getCoachDetails().getTopics().stream().map(TopicAssociation::getTopic).toList();
        if (!topics.contains(topic)) {
            throw new InvalidTopicException();
        }
    }

    public void assertThisTopicIsNewForCoach(CoachDetails coachDetails, Topic topic) {
        for (TopicAssociation topicAssociation : coachDetails.getTopics()) {
            if (topicAssociation.getTopic().equals(topic)) {
                throw new CoachAllreadyContainsTopicException();
            }
        }
    }

    public String assertUserIdIsPartOfSessionAsCoachOrCoachee(UUID userId, Session session) {
        UUID coachId = session.getCoach().getUserId();
        UUID coacheeId = session.getCoachee().getUserId();
        if (coachId.equals(userId)) {
            return "COACH";
        } else if (coacheeId.equals(userId)) {
            return "COACHEE";
        } else {
            throw new InvalidTokenException();
        }
    }

    public void assertValidToken(String userId, String authorization) {
        UUID uuid = getValidUserIdOrThrowBadRequest(authorization);
        String token = uuid.toString();
        if (!token.equals(userId)) {
            throw new InvalidTokenException();
        }
    }

    public UUID getValidUserIdOrThrowBadRequest(String authorizationToken) {
        try {
            return getUuidFromToken(authorizationToken);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token");
        }
    }

    private UUID getUuidFromToken(String authorizationToken) {
        authorizationToken = removeBearerPrefixFromToken(authorizationToken);

        String payload = getPayloadFromToken(authorizationToken);
        String decodedPayload = decodePayload(payload);
        return UUID.fromString(getUuidFromDecodedPayload(decodedPayload));
    }

    private String getUuidFromDecodedPayload(String decodedpayload) {
        final String SUBJECT_PREFIX = "\"sub\":\"";
        final int indexOfSubjectAfterSub = decodedpayload.indexOf(SUBJECT_PREFIX) + SUBJECT_PREFIX.length();
        final int lengthOfUUID = 36;

        return decodedpayload.substring(indexOfSubjectAfterSub, indexOfSubjectAfterSub + lengthOfUUID);
    }

    private String decodePayload(String payload) { //Can throw an error if payload is not 64-bit long
        Base64.Decoder decoder = Base64.getDecoder();
        payload = new String(decoder.decode(payload));
        return payload;
    }

    private String removeBearerPrefixFromToken(String authorizationToken) {
        return authorizationToken.substring(7);
    }

    private String getPayloadFromToken(String authorizationToken) {
        //To get the payload from the token, each part of the token is a separated by a dot.
        // We can thus split the token into separate parts each time we find a dot in the string
        //The body is the second part of the payload, which we return.
        String[] chunks = authorizationToken.split("\\.");
        return chunks[1];
    }


}

