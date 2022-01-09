package com.switchfully.teamair.codecoach.domain.repositories;

import com.switchfully.teamair.codecoach.domain.entities.Role;
import com.switchfully.teamair.codecoach.domain.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    User findByEmail(String email);
    List<User> findAll();
    List<User> findAllByRolesContaining(Role role);
    User findUserByUserId(UUID userId);


}
