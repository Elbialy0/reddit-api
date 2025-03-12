package com.elbialy.reddit.mapper;


import com.elbialy.reddit.dto.PostRequest;
import com.elbialy.reddit.dto.PostResponse;
import com.elbialy.reddit.model.*;
import com.elbialy.reddit.repository.CommentRepository;
import com.elbialy.reddit.repository.UserRepository;
import com.elbialy.reddit.repository.VoteRepository;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.ocpsoft.prettytime.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.Optional;

import static com.elbialy.reddit.model.VoteType.DOWNVOTE;
import static com.elbialy.reddit.model.VoteType.UPVOTE;


@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);
    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName",source = "user.username")
    @Mapping(target = "commentCount" , expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse postToDto(Post post);
    Integer commentCount(Post post){
        return commentRepository.findAllByPost(post).size();
    }
    String getDuration(Post post){
        PrettyTime prettyTime = new PrettyTime();
        return prettyTime.format(new Date(post.getCreatedDate().toEpochMilli()));
    }
    boolean isPostUpVoted(Post post){
        return checkVoteType(post,UPVOTE);
    }
    boolean isPostDownVoted(Post post){
        return checkVoteType(post,DOWNVOTE);
    }
    private boolean checkVoteType(Post post , VoteType voteType){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if((!(authentication instanceof AnonymousAuthenticationToken))&&authentication.isAuthenticated()){
            Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,userRepository.findUserByUsername(authentication.getName()));
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
        }
        return false;

    }


}
