package com.switchfully.teamair.codecoach.api.dtos;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackDtoRequest {

  @NotBlank(message = "Feedback can not be empty")
  String feedback;

}
