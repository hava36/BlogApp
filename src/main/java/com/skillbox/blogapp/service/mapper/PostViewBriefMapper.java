package com.skillbox.blogapp.service.mapper;

import com.skillbox.blogapp.model.entity.PostView;
import com.skillbox.blogapp.service.dto.post.PostResponseListItem;
import com.skillbox.blogapp.service.mapper.user.DefaultUserMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link PostView} and its DTO {@link PostResponseListItem}.
 */
@Mapper(componentModel = "spring", uses = {DefaultUserMapper.class, TagMapper.class, PostViewBriefMapper.class,
    PostVoteMapper.class, PostCommentMapper.class})
public interface PostViewBriefMapper extends EntityMapper<PostResponseListItem, PostView> {

    @Mapping(target = "user", source = "user", qualifiedByName = "idName")
    @Mapping(target = "timestamp", expression = "java(postView.getTime().getEpochSecond())")
    @Mapping(target = "commentCount", source = "commentCount")
    @Mapping(target = "likeCount", source = "likeCount")
    @Mapping(target = "dislikeCount", source = "dislikeCount")
    @Mapping(target = "announce", source = "text")
    @Mapping(target = "isActive", source = "isActive")
    PostResponseListItem toDto(PostView postView);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostResponseListItem toDtoId(PostView postView);

}
