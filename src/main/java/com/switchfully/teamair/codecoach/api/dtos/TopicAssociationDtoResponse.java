package com.switchfully.teamair.codecoach.api.dtos;

import com.switchfully.teamair.codecoach.domain.entities.CoachDetails;
import com.switchfully.teamair.codecoach.domain.entities.Topic;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopicAssociationDtoResponse {

    int topicIdFk;
    int coachDetailsIdFk;
    int coachKnowledgeLevel;
    TopicDtoResponse topic;

}
