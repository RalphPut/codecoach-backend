package com.switchfully.teamair.codecoach.api.dtos;


import com.switchfully.teamair.codecoach.domain.entities.Role;
import com.switchfully.teamair.codecoach.domain.entities.RoleStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class RoleDtoResponse {

    RoleStatus roleStatus;
}
