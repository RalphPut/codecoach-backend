package com.switchfully.teamair.codecoach.services.mappers;

import com.switchfully.teamair.codecoach.api.dtos.RoleDtoResponse;
import com.switchfully.teamair.codecoach.domain.entities.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDtoResponse toRoleDtoResponse(Role role) {
        return RoleDtoResponse.builder()
                .roleStatus(role.getAuthority())
                .build();
    }
}
