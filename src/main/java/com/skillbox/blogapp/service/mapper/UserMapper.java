package com.skillbox.blogapp.service.mapper;

import com.skillbox.blogapp.model.entity.User;
import com.skillbox.blogapp.service.dto.UserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link User} and its DTO {@link UserDto}.
 */
@Mapper(componentModel = "spring", uses = {PostDetailedMapper.class})
public interface UserMapper extends EntityMapper<UserDto, User> {

    User toEntity(UserDto dto);

    default User toEntity(UserDto dto, String encodingPassword) {
        dto.setPassword(encodingPassword);
        return toEntity(dto);
    }

    UserDto toDto(User entity);

    @Named("idName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    UserDto toDtoIdName(User entity);

}
