package com.switchfully.teamair.codecoach.domain.repositories;

import com.switchfully.teamair.codecoach.domain.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {

    List<Session> findAll();

    Session findSessionById(UUID sessionId);
}
