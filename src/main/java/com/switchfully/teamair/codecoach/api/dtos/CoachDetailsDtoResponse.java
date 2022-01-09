package com.switchfully.teamair.codecoach.api.dtos;

import com.switchfully.teamair.codecoach.domain.entities.TopicAssociation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoachDetailsDtoResponse {

    int id;
    String availableTime;
    Integer coachXp;
    String introduction;
    List<TopicAssociationDtoResponse> topics;

}
