package com.elbialy.reddit.service;

import com.elbialy.reddit.dto.CommentsDto;
import com.elbialy.reddit.model.Post;
import com.elbialy.reddit.model.User;
import com.elbialy.reddit.repository.CommentRepository;
import com.elbialy.reddit.repository.PostRepository;
import com.elbialy.reddit.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CommentsService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Transactional
    public void createComment(CommentsDto commentsDto) {
        Post post  = postRepository.findById(commentsDto.getPostId()).orElseThrow(()->new RuntimeException("Post not found"));
        User user = userRepository.findUserByUsername(commentsDto.getUserName()).orElseThrow(()->new RuntimeException("User not found"));

    }
}
