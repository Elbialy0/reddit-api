package com.elbialy.reddit.service;

import com.elbialy.reddit.dto.SubRedditDto;
import com.elbialy.reddit.model.Subreddit;
import com.elbialy.reddit.repository.SubRedditRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubRedditService {
    private final SubRedditRepository subRedditRepository;

    @Transactional
    public SubRedditDto save(SubRedditDto subRedditDto) {
        Subreddit subreddit = subRedditRepository.save(mapSubRedditDto(subRedditDto));
        subRedditDto.setId(subreddit.getId());
        return subRedditDto;
    }
   
    public List<SubRedditDto> getAll(){
        return subRedditRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());

    }

    private SubRedditDto mapToDto(Subreddit subreddit) {
        return SubRedditDto.builder().name(subreddit.getName()).description(subreddit.getDescription()).id(subreddit.getId()).build();
    }

    private Subreddit mapSubRedditDto(SubRedditDto subRedditDto) {
        return Subreddit.builder().name(subRedditDto.getName()).description(subRedditDto.getDescription()).build();
    }
}

