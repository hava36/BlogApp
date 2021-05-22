package com.skillbox.blogapp.service.mapper;

import com.skillbox.blogapp.model.dto.PostDto;
import com.skillbox.blogapp.model.entity.PostView;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link PostView} and its DTO {@link PostDto}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TagMapper.class, PostViewMapper.class,
    PostVoteMapper.class, PostCommentMapper.class})
public interface PostViewMapper extends EntityMapper<PostDto, PostView> {

    @Mapping(target = "user", source = "user", qualifiedByName = "idName")
    @Mapping(target = "timestamp", expression = "java(postView.getTime().getEpochSecond())")
    @Mapping(target = "commentCount", source = "commentCount")
    @Mapping(target = "likeCount", source = "likeCount")
    @Mapping(target = "dislikeCount", source = "dislikeCount")
    @Mapping(target = "announce", source = "text")
    @Mapping(target = "isActive", source = "isActive")
    PostDto toDto(PostView postView);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostDto toDtoId(PostView postView);

}
