package com.elbialy.reddit.service;

import com.elbialy.reddit.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CommentsService {
    private final CommentRepository commentRepository;
    @Transactional
    public
}
