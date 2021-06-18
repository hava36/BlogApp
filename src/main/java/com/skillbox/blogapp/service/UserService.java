package com.skillbox.blogapp.service;

import com.skillbox.blogapp.model.response.RegistrationResponse;
import com.skillbox.blogapp.service.dto.UserDto;
import java.util.List;
import java.util.Optional;

public interface UserService {

    RegistrationResponse register(UserDto userDto);

    UserDto save(UserDto userDto);

    Optional<UserDto> partialUpdate(UserDto userDto);

    List<UserDto> findAll();

    Optional<UserDto> findOne(Integer id);

    void delete(Integer id);

}
