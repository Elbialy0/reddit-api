package com.elbialy.reddit.controller;

import com.elbialy.reddit.dto.SubRedditDto;
import com.elbialy.reddit.service.SubRedditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubRedditController {
    private final SubRedditService subRedditService;
    @PostMapping
    public ResponseEntity<SubRedditDto> createSubReddit(@RequestBody SubRedditDto subRedditDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(subRedditService.save(subRedditDto));
    }
    @GetMapping
    public ResponseEntity<SubRedditDto> getAllSubReddit(){
        return null;

    }
}
