package com.elbialy.reddit.mapper;

import com.elbialy.reddit.dto.SubRedditDto;
import com.elbialy.reddit.model.Post;
import com.elbialy.reddit.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubRedditMapper {
    SubRedditMapper INSTANCE = Mappers.getMapper(SubRedditMapper.class);
    @Mapping(target = "numberOfPosts",expression = "java(mapPosts(subreddit.getPosts()))")
    SubRedditDto SubRedditToDTO(Subreddit subreddit);
    default Integer maPosts(List<Post>posts){
        return posts.size();
    }
    @InheritInverseConfiguration
    @Mapping(target = "posts",ignore = true)
    Subreddit dtoToSubReddit(SubRedditDto subRedditDto);
}
