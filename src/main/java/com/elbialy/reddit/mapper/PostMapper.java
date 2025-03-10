package com.elbialy.reddit.mapper;


import com.elbialy.reddit.dto.PostRequest;
import com.elbialy.reddit.model.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
    PostRequest postToPostRequest(Post post);
    @InheritInverseConfiguration
    Post postRequestToPost(PostRequest postRequest);

}
