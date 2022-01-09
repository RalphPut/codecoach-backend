package com.switchfully.teamair.codecoach.api.dtos;

import com.switchfully.teamair.codecoach.domain.entities.SessionType;
import javax.validation.constraints.NotBlank;
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
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SessionDtoRequest {

  @NotBlank(message = "Coach id can not be empty")
  String coachId;
  @NotBlank(message = "Coachee id can not be empty")
  String coacheeId;
  @NotBlank(message = "Topic name can not be empty")
  String topic; //name
  //    @NotBlank(message = "Date and time can not be empty")
//    LocalDateTime dateTime;
  @NotBlank(message = "Data can not be empty")
  String date;
  @NotBlank(message = "Time can not be empty")
  String time;

  @NotBlank(message = "Session type can not be empty")
  SessionType sessionType;

  String remarks;

}
