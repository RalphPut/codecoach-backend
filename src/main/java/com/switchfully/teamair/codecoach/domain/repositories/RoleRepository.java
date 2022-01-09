package com.switchfully.teamair.codecoach.domain.repositories;

import com.switchfully.teamair.codecoach.domain.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository <Role, Integer> {

    Role findRoleById(int id);
}
