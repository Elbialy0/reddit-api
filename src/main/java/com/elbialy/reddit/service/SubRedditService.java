package com.elbialy.reddit.service;

import com.elbialy.reddit.dto.SubRedditDto;
import com.elbialy.reddit.exceptions.SpringRedditException;
import com.elbialy.reddit.mapper.SubRedditMapper;
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
    private final SubRedditMapper subRedditMapper;

    @Transactional
    public SubRedditDto save(SubRedditDto subRedditDto) {
        Subreddit subreddit = subRedditRepository.save(subRedditMapper.dtoToSubReddit(subRedditDto));
        subRedditDto.setId(subreddit.getId());
        return subRedditDto;
    }
   
    public List<SubRedditDto> getAll(){
        return subRedditRepository.findAll().stream().map(subRedditMapper::SubRedditToDTO).collect(Collectors.toList());

    }
    public SubRedditDto getSubReddit(Long id){
        return subRedditMapper.SubRedditToDTO(subRedditRepository.findById(id)
                .orElseThrow(()->new SpringRedditException("Subreddit not found")));
    }




}

