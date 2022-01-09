package com.switchfully.teamair.codecoach.api.dtos;


import com.switchfully.teamair.codecoach.domain.entities.TopicAssociation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoachDetailsDtoRequest {

  @NotBlank(message = "Available time can not be empty")
  String availableTime;
  @NotNull
  Integer coachXp;
  @NotBlank(message = "Introduction can not be empty")
  String introduction;

  TopicAssociationDtoRequest topics;
}
