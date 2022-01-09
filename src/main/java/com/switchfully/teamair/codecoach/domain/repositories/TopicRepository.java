package com.switchfully.teamair.codecoach.domain.repositories;

import com.switchfully.teamair.codecoach.domain.entities.Topic;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, Integer> {

    Topic findTopicById(int topicId);
    Topic findTopicByName(String name);
}
