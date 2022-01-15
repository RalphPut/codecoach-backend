package com.switchfully.teamair.codecoach.services.mappers;

import com.switchfully.teamair.codecoach.api.dtos.SessionDtoRequest;
import com.switchfully.teamair.codecoach.api.dtos.SessionDtoResponse;
import com.switchfully.teamair.codecoach.domain.entities.Feedback;
import com.switchfully.teamair.codecoach.domain.entities.Session;
import com.switchfully.teamair.codecoach.domain.entities.SessionStatus;
import com.switchfully.teamair.codecoach.domain.repositories.TopicRepository;
import com.switchfully.teamair.codecoach.domain.repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class SessionMapper {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public SessionMapper(UserRepository userRepository, TopicRepository topicRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;

    }

    public Session toEntity(SessionDtoRequest sessionDtoRequest) {
        String dateWithTimeZonesAttached = sessionDtoRequest.getDate();
        String date = dateWithTimeZonesAttached.substring(0, 10);

        String str = date + " " + sessionDtoRequest.getTime();
        System.out.println(str);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        return Session.builder()
                .coach(userRepository.findUserByUserId(UUID.fromString(sessionDtoRequest.getCoachId())))
                .coachee(userRepository.findUserByUserId(UUID.fromString(sessionDtoRequest.getCoacheeId())))
                .dateTime(dateTime)
                .sessionStatus(SessionStatus.REQUESTED)
                .sessionType(sessionDtoRequest.getSessionType())
                .remarks(sessionDtoRequest.getRemarks())
                .topic(topicRepository.findTopicByName(sessionDtoRequest.getTopic()))
                .build();

    }

    public SessionDtoResponse toResponse(Session session) {
        return SessionDtoResponse.builder()
                .sessionId(session.getId().toString())
                .coachId(session.getCoach().getUserId().toString())
                .coacheeId(session.getCoachee().getUserId().toString())
                .topic(session.getTopic().getName())
                .date(session.getDateTime().toLocalDate().toString())
                .time(session.getDateTime().toLocalTime().toString())
                .sessionStatus(session.getSessionStatus())
                .sessionType(session.getSessionType())
                .remarks(session.getRemarks())
                .feedbackFromCoach(session.getFeedback().getFeedbackFromCoach())
                .feedbackFromCoachee(session.getFeedback().getFeedbackFromCoachee())
                .build();
    }
}
