package com.switchfully.teamair.codecoach.api.dtos;

import javax.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDtoRequest {

  @NotBlank(message = "Feedback can not be empty")
  String feedback;

}
