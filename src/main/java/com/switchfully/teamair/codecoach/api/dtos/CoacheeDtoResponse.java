package com.switchfully.teamair.codecoach.api.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class    CoacheeDtoResponse {
    String firstName;
    String lastName;
    String email;
    String imageUrl;
}
