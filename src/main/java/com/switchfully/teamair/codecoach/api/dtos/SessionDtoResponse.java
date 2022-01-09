package com.switchfully.teamair.codecoach.api.dtos;

import com.switchfully.teamair.codecoach.domain.entities.SessionStatus;
import com.switchfully.teamair.codecoach.domain.entities.SessionType;
import com.switchfully.teamair.codecoach.domain.entities.Topic;
import com.switchfully.teamair.codecoach.domain.entities.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SessionDtoResponse {

    String sessionId;
    String coachId;
    String coacheeId;
    String topic;
    String date;
    String time;
    SessionType sessionType;
    SessionStatus sessionStatus;
    String remarks;
}
