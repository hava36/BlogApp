package com.skillbox.blogapp.service.mapper;

import com.skillbox.blogapp.model.entity.Post;
import com.skillbox.blogapp.service.dto.post.PostDetailedItem;
import com.skillbox.blogapp.service.dto.post.PostResponseListItem;
import com.skillbox.blogapp.service.mapper.user.DefaultUserMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostResponseListItem}.
 */
@Mapper(componentModel = "spring", uses = {DefaultUserMapper.class, TagMapper.class, PostDetailedMapper.class,
    PostVoteMapper.class, PostCommentMapper.class})
public interface PostDetailedMapper extends EntityMapper<PostDetailedItem, Post> {

    @Mapping(target = "user", source = "user", qualifiedByName = "idName")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "comments", source = "comments")
    @Mapping(target = "votes", source = "votes")
    @Mapping(target = "timestamp", expression = "java(post.getTime().getEpochSecond())")
    @Mapping(target = "commentCount", expression = "java(post.getComments().size())")
    @Mapping(target = "likeCount", expression = "java(post.getVotes().stream().filter(postVote -> postVote.getValue().equals(1)).count())")
    @Mapping(target = "dislikeCount", expression = "java(post.getVotes().stream().filter(postVote -> postVote.getValue().equals(-1)).count())")
    @Mapping(target = "announce", source = "text")
    PostDetailedItem toDto(Post post);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostDetailedItem toDtoId(Post post);

    @Mapping(target = "moderator", source = "moderator")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "comments", source = "comments")
    @Mapping(target = "votes", source = "votes")
    Post toEntity(PostResponseListItem postResponseListItem);

}
