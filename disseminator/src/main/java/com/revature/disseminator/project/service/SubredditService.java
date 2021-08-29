package com.revature.disseminator.project.service;

import com.revature.disseminator.project.controller.AuthController;
import com.revature.disseminator.project.dto.SubredditDto;
import com.revature.disseminator.project.exceptions.SpringRedditException;
import com.revature.disseminator.project.mapper.SubredditMapper;
import com.revature.disseminator.project.model.Subreddit;
import com.revature.disseminator.project.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(SubredditService.class);
    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subpost found with ID - " + id));
        LOGGER.error("No subpost found ");
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
