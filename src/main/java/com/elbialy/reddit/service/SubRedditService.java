package com.elbialy.reddit.service;

import com.elbialy.reddit.dto.SubRedditDto;
import com.elbialy.reddit.model.Subreddit;
import com.elbialy.reddit.repository.SubRedditRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    private Subreddit mapSubRedditDto(SubRedditDto subRedditDto) {
        return Subreddit.builder().name(subRedditDto.getName()).description(subRedditDto.getDescription()).build();
    }

}
