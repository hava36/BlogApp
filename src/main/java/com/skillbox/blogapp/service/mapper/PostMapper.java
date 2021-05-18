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
    PostVoteMapper.class, PostCommentMapper.class})
public interface PostMapper extends EntityMapper<PostDto, Post> {

    @Mapping(target = "user", source = "user", qualifiedByName = "idName")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "comments", source = "comments")
    @Mapping(target = "votes", source = "votes")
    @Mapping(target = "timestamp", expression = "java(post.getTime().getEpochSecond())")
    @Mapping(target = "commentCount", expression = "java(post.getComments().size())")
    @Mapping(target = "likeCount", expression = "java(post.getVotes().stream().filter(postVote -> postVote.getValue().equals(1)).count())")
    @Mapping(target = "dislikeCount", expression = "java(post.getVotes().stream().filter(postVote -> postVote.getValue().equals(-1)).count())")
    @Mapping(target = "announce", source = "text")
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
