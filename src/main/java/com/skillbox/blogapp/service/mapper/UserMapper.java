package com.skillbox.blogapp.service.mapper;

import com.skillbox.blogapp.model.dto.UserDto;
import com.skillbox.blogapp.model.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link User} and its DTO {@link UserDto}.
 */
@Mapper(componentModel = "spring", uses = {PostMapper.class})
public interface UserMapper extends EntityMapper<UserDto, User> {

    @Mapping(target = "posts", source = "posts")
    User toEntity(UserDto dto);

    @Mapping(target = "posts", source = "posts")
    UserDto toDto(User entity);

    @Named("idName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    UserDto toDtoIdName(User entity);

}
