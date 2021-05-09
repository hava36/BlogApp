package com.skillbox.blogapp.service.mapper;

import com.skillbox.blogapp.model.dto.PostDto;
import com.skillbox.blogapp.model.entity.Post;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDto}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TagMapper.class, PostMapper.class,
    PostVoteMapper.class})
public interface PostMapper extends EntityMapper<PostDto, Post> {

    @Mapping(target = "moderator", source = "moderator")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "comments", source = "comments")
    @Mapping(target = "votes", source = "votes")
    PostDto toDto(Post post);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostDto toDtoId(Post post);

    @Mapping(target = "moderator", source = "moderator")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "comments", source = "comments")
    @Mapping(target = "votes", source = "votes")
    Post toEntity(PostDto postDto);

}
