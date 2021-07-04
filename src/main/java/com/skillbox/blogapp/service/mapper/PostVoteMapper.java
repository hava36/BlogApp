package com.skillbox.blogapp.service.mapper;

import com.skillbox.blogapp.model.entity.PostVote;
import com.skillbox.blogapp.service.dto.PostVoteDto;
import com.skillbox.blogapp.service.mapper.user.DefaultUserMapper;
import java.util.Set;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link PostVote} and its DTO {@link PostVoteDto}.
 */
@Mapper(componentModel = "spring", uses = {DefaultUserMapper.class, PostDetailedMapper.class})
public interface PostVoteMapper extends EntityMapper<PostVoteDto, PostVote> {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "post", source = "post")
    PostVoteDto toDto(PostVote s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostVoteDto toDtoId(PostVote postComment);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<PostVoteDto> toDtoIdSet(Set<PostVote> postComment);

}
