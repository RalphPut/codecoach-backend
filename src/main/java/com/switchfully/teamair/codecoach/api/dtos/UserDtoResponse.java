package com.switchfully.teamair.codecoach.api.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;


@Builder
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDtoResponse {
    UUID userId;
    String firstName;
    String lastName;
    String email;
    String imageUrl;
    String company;
    List<RoleDtoResponse> roleStatus;
    CoachDetailsDtoResponse coachDetails;

}
