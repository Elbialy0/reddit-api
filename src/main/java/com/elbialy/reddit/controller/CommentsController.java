package com.elbialy.reddit.controller;

import com.elbialy.reddit.dto.CommentsDto;
import com.elbialy.reddit.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;
    @PostMapping
    public ResponseEntity<String> createComment(CommentsDto commentsDto){
        commentsService.createComment(commentsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comment sent successfully");
    }
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(commentsService.getAllCommentsForPost(postId));
    }

}
