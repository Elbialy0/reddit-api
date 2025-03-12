package com.elbialy.reddit.controller;

import com.elbialy.reddit.dto.VoteDto;
import com.elbialy.reddit.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor

public class VoteController {
    private final VoteService voteService;
    @PostMapping
    public ResponseEntity<String > vote(@RequestBody VoteDto voteDto) throws Exception{
        voteService.vote(voteDto);
        return ResponseEntity.status(HttpStatus.OK).body("Voted");

    }
}
