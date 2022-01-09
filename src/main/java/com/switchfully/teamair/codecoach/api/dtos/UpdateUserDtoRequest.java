package com.switchfully.teamair.codecoach.api.dtos;

import com.switchfully.teamair.codecoach.domain.entities.CoachDetails;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserDtoRequest {

    String firstName;
    String lastName;
    @Email
    String email;
    String imageUrl;
    String company;

    CoachDetailsDtoRequest coachDetails;
}
