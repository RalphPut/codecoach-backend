package com.switchfully.teamair.codecoach.api.dtos;


import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;

@Builder
@Data
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackDtoResponse {

  UUID feedbackId;
  String feedbackCoach;
  String feedbackCoachee;
}
