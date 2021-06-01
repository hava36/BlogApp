package com.skillbox.blogapp.service.mapper;

import com.skillbox.blogapp.model.dto.PostCommentDto;
import com.skillbox.blogapp.model.entity.PostComment;
import java.util.Set;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link PostComment} and its DTO {@link PostCommentDto}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PostDetailedMapper.class})
public interface PostCommentMapper extends EntityMapper<PostCommentDto, PostComment> {

    @Mapping(target = "parent", source = "parent")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "post", source = "post")
    PostCommentDto toDto(PostComment s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostCommentDto toDtoId(PostComment postComment);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<PostCommentDto> toDtoIdSet(Set<PostComment> comments);

}
