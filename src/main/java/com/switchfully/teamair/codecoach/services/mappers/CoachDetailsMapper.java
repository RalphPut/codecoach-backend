package com.switchfully.teamair.codecoach.services.mappers;

import com.switchfully.teamair.codecoach.api.dtos.CoachDetailsDtoRequest;
import com.switchfully.teamair.codecoach.api.dtos.CoachDetailsDtoResponse;
import com.switchfully.teamair.codecoach.domain.entities.CoachDetails;
import org.springframework.stereotype.Component;

@Component
public class CoachDetailsMapper {

    private TopicAssociationMapper topicAssociationMapper;

    public CoachDetailsMapper(TopicAssociationMapper topicAssociationMapper) {
        this.topicAssociationMapper = topicAssociationMapper;
    }

    public CoachDetailsDtoResponse toResponse(CoachDetails coachDetails) {
        return CoachDetailsDtoResponse.builder()
                .introduction((coachDetails.getIntroduction()))
                .availableTime(coachDetails.getAvailableTime())
                .coachXp(coachDetails.getCoachXp())
                .id(coachDetails.getId())
                .topics(coachDetails.getTopics().stream().map(topicAssociationMapper::toResponse).toList())
                .build();
    }

    public CoachDetails toEntity(CoachDetailsDtoRequest coachDetailsDtoRequest) {
        if (coachDetailsDtoRequest.getTopics() == null) {
            return CoachDetails.builder()
                    .introduction(coachDetailsDtoRequest.getIntroduction())
                    .availableTime(coachDetailsDtoRequest.getAvailableTime())
                    .coachXp(coachDetailsDtoRequest.getCoachXp())
                    .build();
        } else {
            return CoachDetails.builder()
                    .introduction(coachDetailsDtoRequest.getIntroduction())
                    .availableTime(coachDetailsDtoRequest.getAvailableTime())
                    .coachXp(coachDetailsDtoRequest.getCoachXp())
//                    .topics(coachDetailsDtoRequest.getTopicAssociationDtoRequests().stream().map(topicAssociationMapper::toEntity).toList())
                    .build();
        }
    }
}
