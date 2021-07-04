package com.skillbox.blogapp.service;

import com.skillbox.blogapp.model.response.RegistrationResponse;
import com.skillbox.blogapp.service.dto.UserDto;
import com.skillbox.blogapp.service.dto.auth.login.request.LoginRequest;
import com.skillbox.blogapp.service.dto.auth.login.response.LoginResponseSuccess;
import java.util.List;
import java.util.Optional;

public interface UserService {

    RegistrationResponse register(UserDto userDto);

    UserDto save(UserDto userDto);

    Optional<UserDto> partialUpdate(UserDto userDto);

    List<UserDto> findAll();

    Optional<UserDto> findOne(Integer id);

    Optional<UserDto> findOneBySessionId(String sessionId);

    void delete(Integer id);

    Optional<LoginResponseSuccess> login(LoginRequest loginRequest);

    void logout(String sessionId);

}
