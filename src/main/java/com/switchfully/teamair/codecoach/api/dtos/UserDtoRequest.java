package com.switchfully.teamair.codecoach.api.dtos;


import com.switchfully.teamair.codecoach.domain.entities.CoachDetails;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Builder
@Data
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDtoRequest {

    @NotBlank(message = "First name can not be empty")
    String firstName;
    @NotBlank(message = "Last name can not be empty")
    String lastName;
    @NotBlank(message = "Email can not be empty")
    @Email
    String email;
    String imageUrl;
    @NotBlank(message = "Password can not be empty")
    String password;
    String company;
    @Valid
    CoachDetailsDtoRequest coachDetailsDtoRequest;
}
