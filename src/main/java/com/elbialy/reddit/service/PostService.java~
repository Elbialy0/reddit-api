package com.elbialy.reddit.service;

import com.elbialy.reddit.dto.PostRequest;
import com.elbialy.reddit.dto.PostResponse;
import com.elbialy.reddit.exceptions.SpringRedditException;
import com.elbialy.reddit.exceptions.UserNotFoundException;
import com.elbialy.reddit.mapper.PostMapper;
import com.elbialy.reddit.model.Post;
import com.elbialy.reddit.model.Subreddit;
import com.elbialy.reddit.model.User;
import com.elbialy.reddit.repository.PostRepository;
import com.elbialy.reddit.repository.SubRedditRepository;
import com.elbialy.reddit.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SubRedditRepository subRedditRepository;
    private final PostMapper postMapper;
    @Transactional
    public PostResponse createPost(PostRequest postRequest){
        User user = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                ()-> new UserNotFoundException("User not found")
        );
        log.info("User {} is creating post",user.getUsername());
        Post post = postMapper.map(postRequest,subRedditRepository.findSubRedditByName(postRequest.getSubredditName()).
                orElseThrow(()->new SpringRedditException("Subreddit not found")),user);
        return postMapper.postToDto(postRepository.save(post));
    }
    public PostResponse getPost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new SpringRedditException("Post not found"));
        return postMapper.postToDto(post);
    }
    public List<PostResponse> getAllPosts(){
        return postRepository.findAll().stream()
                .map(postMapper::postToDto)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPostsBySubreddit(Long subredditId){
        Subreddit subreddit = subRedditRepository.findById(subredditId)
                .orElseThrow(()->new SpringRedditException("Subreddit not found"));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::postToDto).collect(Collectors.toList());

    }
    public List<PostResponse> getPostsByUsername(String username){
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(()->new SpringRedditException("User not found"));
        List<Post> posts = postRepository.findAllByUser(user);
        return  posts.stream().map(postMapper::postToDto).collect(Collectors.toList());
    }
}
