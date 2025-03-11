package com.elbialy.reddit.service;

import com.elbialy.reddit.dto.VoteDto;
import com.elbialy.reddit.exceptions.SpringRedditException;
import com.elbialy.reddit.model.Post;
import com.elbialy.reddit.model.Vote;
import com.elbialy.reddit.repository.PostRepository;
import com.elbialy.reddit.repository.UserRepository;
import com.elbialy.reddit.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.elbialy.reddit.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(()->new RuntimeException("Post not found"));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUser(post,userRepository
                .findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(()->new RuntimeException("User not found") ));
        if (voteByPostAndUser.isPresent()&& voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already "+voteDto.getVoteType()+"ed for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount()+1);
        }
        else {
            post.setVoteCount(post.getVoteCount()-1);
        }
        voteRepository.save(mapToVote(voteDto , post));


    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new RuntimeException("User not found")))
                .build();
    }
}
