package com.switchfully.teamair.codecoach.services;

import com.switchfully.teamair.codecoach.api.dtos.TopicDtoResponse;
import com.switchfully.teamair.codecoach.domain.repositories.TopicRepository;
import com.switchfully.teamair.codecoach.services.mappers.TopicMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public TopicService(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }

    public List<TopicDtoResponse> getAllTopics(){
        return topicRepository.findAll().stream().map(topicMapper::toResponse).collect(Collectors.toList());
    }
}
