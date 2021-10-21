package com.skillbox.blogapp.service.impl;

import com.skillbox.blogapp.config.Constants;
import com.skillbox.blogapp.model.entity.User;
import com.skillbox.blogapp.model.response.RegistrationResponse;
import com.skillbox.blogapp.repository.UserRepository;
import com.skillbox.blogapp.service.CaptchaCodeService;
import com.skillbox.blogapp.service.UserService;
import com.skillbox.blogapp.service.dto.CaptchaCodeDto;
import com.skillbox.blogapp.service.dto.UserDto;
import com.skillbox.blogapp.service.dto.auth.login.request.LoginRequest;
import com.skillbox.blogapp.service.dto.auth.login.response.LoginResponse;
import com.skillbox.blogapp.service.mapper.user.DefaultUserMapper;
import com.skillbox.blogapp.service.mapper.user.LoginUserMapper;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final AuthenticationManager authenticationManager;

    private final UserRepository usersRepository;

    private final CaptchaCodeService captchaCodeService;

    private final DefaultUserMapper defaultUserMapper;

    private final LoginUserMapper loginUserMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public RegistrationResponse register(UserDto userDto) {
        Map<String, String> errors = checkRegistration(userDto);
        if (errors.isEmpty()) {
            User user = defaultUserMapper.toEntity(userDto, passwordEncoder.encode(userDto.getPassword()));
            usersRepository.save(user);
        }
        return new RegistrationResponse(errors.isEmpty(), errors);
    }

    @Override
    public UserDto save(UserDto userDto) {
        log.debug("Request to save Users : {}", userDto);
        var users = defaultUserMapper.toEntity(userDto);
        users = usersRepository.save(users);
        return defaultUserMapper.toDto(users);
    }

    @Override
    public Optional<UserDto> partialUpdate(UserDto userDto) {
        log.debug("Request to partially update Users : {}", userDto);

        return usersRepository
            .findById(userDto.getId())
            .map(
                existingUsers -> {
                    defaultUserMapper.partialUpdate(existingUsers, userDto);
                    return existingUsers;
                }
            )
            .map(usersRepository::save)
            .map(defaultUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        log.debug("Request to get all Users");
        return usersRepository.findAll().stream().map(defaultUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> findOne(Integer id) {
        log.debug("Request to get Users : {}", id);
        return usersRepository.findById(id).map(defaultUserMapper::toDto);
    }

    @Override
    public Optional<UserDto> findAuthenticatedUser() {

        if (!principalIsValid()) {
            return Optional.empty();
        }

        org.springframework.security.core.userdetails.User principal =
            (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        return usersRepository.findByName(principal.getUsername()).map(defaultUserMapper::toDto);

    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete Users : {}", id);
        usersRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoginResponse> login(LoginRequest loginRequest) {

        Authentication auth =
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        User currentUser = usersRepository.findByName(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException(user.getUsername()));

        return Optional.of(new LoginResponse(loginUserMapper.toDto(currentUser), true));

    }

    @Override
    public Optional<LoginResponse> check() {

        if (!principalIsValid()) {
            return Optional.empty();
        }

        org.springframework.security.core.userdetails.User principal =
            (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return usersRepository.findByName(principal.getUsername())
            .map(loginUserMapper::toDto)
            .map(user -> new LoginResponse(user, true));

    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

    }

    private Map<String, String> checkRegistration(UserDto userDto) {

        Map<String, String> errors = new HashMap<>();

        Optional<CaptchaCodeDto> optionalCaptcha = captchaCodeService.findValidOneBySecretCode(userDto.getCaptchaCode());

        Optional<User> user = usersRepository.findByEmail(userDto.getEmail());

        if (user.isPresent()) {
            errors.put("e_mail", "the current email already exists");
        }

        if (!userDto.getPassword().matches(Constants.PASSWORD_REGEX)) {
            errors.put("password", "password doesn't match the requirements");
        }

        if (optionalCaptcha.isPresent()) {
            CaptchaCodeDto captchaCodeDto = optionalCaptcha.get();
            if (!captchaCodeDto.getSecretCode().equals(userDto.getCaptchaSecretCode())) {
                errors.put("captcha", "captcha code hasn't matched or expired");
            }
        } else {
            errors.put("captcha", "secret code hasn't found");
        }

        return errors;

    }

    private boolean principalIsValid() {
        return (SecurityContextHolder.getContext().getAuthentication().getPrincipal()
            instanceof org.springframework.security.core.userdetails.User);
    }


}
