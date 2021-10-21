package com.skillbox.blogapp.service.mapper.user;

import com.skillbox.blogapp.model.entity.User;
import com.skillbox.blogapp.model.entity.enums.ModerationStatus;
import com.skillbox.blogapp.service.dto.auth.login.response.LoginResponseUser;
import com.skillbox.blogapp.service.mapper.EntityMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LoginUserMapper extends EntityMapper<LoginResponseUser, User> {

    @Override
    User toEntity(LoginResponseUser dto);

    @Override
    LoginResponseUser toDto(User entity);

    @AfterMapping
    default void toDto(User user, @MappingTarget LoginResponseUser userDto) {

        userDto.setModerationCount(
            user.getPosts().stream().filter(post -> post.getModerationStatus().equals(ModerationStatus.NEW)).count());

        userDto.setModeration(user.getIsModerator().equals(1));
        userDto.setSettings(user.getIsModerator().equals(1));

    }

}
