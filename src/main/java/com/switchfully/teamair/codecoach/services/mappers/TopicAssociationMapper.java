package com.switchfully.teamair.codecoach.services.mappers;

import com.switchfully.teamair.codecoach.api.dtos.TopicAssociationDtoRequest;
import com.switchfully.teamair.codecoach.api.dtos.TopicAssociationDtoResponse;
import com.switchfully.teamair.codecoach.domain.entities.TopicAssociation;
import org.springframework.stereotype.Component;

@Component
public class TopicAssociationMapper {

    private final TopicMapper topicMapper;

    public TopicAssociationMapper(TopicMapper topicMapper) {
        this.topicMapper = topicMapper;
    }

    public TopicAssociationDtoResponse toResponse(TopicAssociation topicAssociation) {
        return TopicAssociationDtoResponse.builder()
                .topicIdFk(topicAssociation.getTopicIdFk())
                .coachDetailsIdFk(topicAssociation.getCoachDetailsIdFk())
                .coachKnowledgeLevel(topicAssociation.getCoachKnowledgeLevel())
                .topic(topicMapper.toResponse(topicAssociation.getTopic()))
                .build();
    }

    public TopicAssociation toEntity(TopicAssociationDtoRequest topicAssociationDtoRequest) {
        if (topicAssociationDtoRequest.getTopic() == null) {
            return TopicAssociation.builder()
                    .coachKnowledgeLevel(topicAssociationDtoRequest.getCoachKnowledgeLevel())
                    .build();
        }
        return TopicAssociation.builder()
                .coachKnowledgeLevel(topicAssociationDtoRequest.getCoachKnowledgeLevel())
                .topic(topicMapper.toEntity(topicAssociationDtoRequest.getTopic()))
                .build();
    }
}
