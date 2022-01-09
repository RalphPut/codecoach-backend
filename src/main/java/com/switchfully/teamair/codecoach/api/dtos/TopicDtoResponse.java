package com.switchfully.teamair.codecoach.api.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopicDtoResponse {

    int id;
    String name;
}
