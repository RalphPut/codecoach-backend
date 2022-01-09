package com.switchfully.teamair.codecoach.services.mappers;

import com.switchfully.teamair.codecoach.api.dtos.TopicDtoRequest;
import com.switchfully.teamair.codecoach.api.dtos.TopicDtoResponse;
import com.switchfully.teamair.codecoach.domain.entities.Topic;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {

    public TopicDtoResponse toResponse(Topic topic) {
        return TopicDtoResponse.builder()
                .id(topic.getId())
                .name(topic.getName())
                .build();
    }

    public Topic toEntity(TopicDtoRequest topicDtoRequest) {
        return Topic.builder()
                .name(topicDtoRequest.getName())
                .build();
    }
}
