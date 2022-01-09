package com.switchfully.teamair.codecoach.domain.repositories;

import com.switchfully.teamair.codecoach.domain.entities.CoachDetails;
import org.springframework.data.repository.CrudRepository;

public interface CoachDetailsRepository extends CrudRepository<CoachDetails, Integer> {

    CoachDetails getCoachDetailsById(String coachDetailsId);
    void deleteCoachDetailsById(Integer id);
}
