package com.elbialy.reddit.service;

import com.elbialy.reddit.dto.CommentsDto;
import com.elbialy.reddit.exceptions.SpringRedditException;
import com.elbialy.reddit.mapper.CommentMapper;
import com.elbialy.reddit.model.Comment;
import com.elbialy.reddit.model.NotificationEmail;
import com.elbialy.reddit.model.Post;
import com.elbialy.reddit.model.User;
import com.elbialy.reddit.repository.CommentRepository;
import com.elbialy.reddit.repository.PostRepository;
import com.elbialy.reddit.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.control.MappingControl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.channels.UnresolvedAddressException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CommentsService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;
    @Transactional
    public void createComment(CommentsDto commentsDto) {
        Post post  = postRepository.findById(commentsDto.getPostId()).orElseThrow(()->new RuntimeException("Post not found"));
        User user = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new

                SpringRedditException("User not found"));
        Comment comment = commentMapper.map(commentsDto,post,user);
        commentRepository.save(comment);
        mailService.sendEmail(new NotificationEmail(user.getUsername() +"Commmented on your post",post.getUser().getEmail(),user.getUsername()+"posted a comment on your post"));
    }
    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
        List<Comment> comments = commentRepository.findAllByPost(post);
        return comments.stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }
    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findUserByUsername(userName).orElseThrow(()->new RuntimeException("User not found"));
        List<Comment> comments = commentRepository.findAllByUser(user);
        return comments.stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new RuntimeException("Comment not found"));
        commentRepository.delete(comment);
    }
}
