package com.elbialy.reddit.controller;

import com.elbialy.reddit.dto.SubRedditDto;
import com.elbialy.reddit.service.SubRedditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<SubRedditDto>> getAllSubReddit(){
        return ResponseEntity.status(HttpStatus.OK).body(subRedditService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SubRedditDto> getSubReddit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(subRedditService.getSubReddit(id));

    }
}
