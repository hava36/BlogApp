package com.skillbox.blogapp.service.mapper;

import com.skillbox.blogapp.model.dto.PostVoteDto;
import com.skillbox.blogapp.model.entity.PostVote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link PostVote} and its DTO {@link PostVoteDto}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PostMapper.class})
public interface PostVoteMapper extends EntityMapper<PostVoteDto, PostVote> {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "post", source = "post")
    PostVoteDto toDto(PostVote s);
}
