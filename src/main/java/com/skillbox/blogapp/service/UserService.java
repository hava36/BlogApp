package com.skillbox.blogapp.service;

import com.skillbox.blogapp.model.response.RegistrationResponse;
import com.skillbox.blogapp.service.dto.UserDto;
import com.skillbox.blogapp.service.dto.auth.login.request.LoginRequest;
import com.skillbox.blogapp.service.dto.auth.login.response.LoginResponse;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    RegistrationResponse register(UserDto userDto);

    UserDto save(UserDto userDto);

    Optional<UserDto> partialUpdate(UserDto userDto);

    List<UserDto> findAll();

    Optional<UserDto> findOne(Integer id);

    Optional<UserDto> findAuthenticatedUser();

    void delete(Integer id);

    Optional<LoginResponse> login(LoginRequest loginRequest);

    Optional<LoginResponse> check();

    void logout(HttpServletRequest request, HttpServletResponse response);

}
