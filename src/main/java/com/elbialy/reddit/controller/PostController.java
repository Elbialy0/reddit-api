package com.elbialy.reddit.controller;

import com.elbialy.reddit.dto.PostRequest;
import com.elbialy.reddit.dto.PostResponse;
import com.elbialy.reddit.model.Post;
import com.elbialy.reddit.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postRequest));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("/subreddit/{subredditId}")
    public ResponseEntity<List<PostResponse>> getAllPostsBySubreddit(@PathVariable Long subredditId){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(subredditId));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostResponse>> getAllPostsByUser(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }

}
