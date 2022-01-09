package com.switchfully.teamair.codecoach.api.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class TopicAssociationDtoRequest {

  @NotNull(message = "Knowledge level cant be empty.")
  Integer coachKnowledgeLevel;
  @Valid
  TopicDtoRequest topic;

}
