package com.skillbox.blogapp.service.impl;

import com.skillbox.blogapp.config.Constants;
import com.skillbox.blogapp.model.dto.CaptchaCodeDto;
import com.skillbox.blogapp.model.dto.UserDto;
import com.skillbox.blogapp.model.entity.User;
import com.skillbox.blogapp.model.response.RegistrationResponse;
import com.skillbox.blogapp.repository.UserRepository;
import com.skillbox.blogapp.service.CaptchaCodeService;
import com.skillbox.blogapp.service.UserService;
import com.skillbox.blogapp.service.mapper.UserMapper;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository usersRepository;

    private final CaptchaCodeService captchaCodeService;

    private final UserMapper usersMapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository usersRepository, CaptchaCodeService captchaCodeService, UserMapper usersMapper,
        PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.captchaCodeService = captchaCodeService;
        this.usersMapper = usersMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegistrationResponse register(UserDto userDto) {
        Map<String, String> errors = checkRegistration(userDto);
        if (errors.isEmpty()) {
            User user = usersMapper.toEntity(userDto, passwordEncoder.encode(userDto.getPassword()));
            usersRepository.save(user);
        }
        return new RegistrationResponse(errors.isEmpty(), errors);
    }

    @Override
    public UserDto save(UserDto userDto) {
        log.debug("Request to save Users : {}", userDto);
        var users = usersMapper.toEntity(userDto);
        users = usersRepository.save(users);
        return usersMapper.toDto(users);
    }

    @Override
    public Optional<UserDto> partialUpdate(UserDto userDto) {
        log.debug("Request to partially update Users : {}", userDto);

        return usersRepository
            .findById(userDto.getId())
            .map(
                existingUsers -> {
                    usersMapper.partialUpdate(existingUsers, userDto);
                    return existingUsers;
                }
            )
            .map(usersRepository::save)
            .map(usersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        log.debug("Request to get all Users");
        return usersRepository.findAll().stream().map(usersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> findOne(Integer id) {
        log.debug("Request to get Users : {}", id);
        return usersRepository.findById(id).map(usersMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete Users : {}", id);
        usersRepository.deleteById(id);
    }

    private Map<String, String> checkRegistration(UserDto userDto) {

        Map<String, String> errors = new HashMap<>();

        Optional<CaptchaCodeDto> optionalCaptcha = captchaCodeService.findValidOneBySecretCode(userDto.getCaptchaCode());

        Optional<User> user = usersRepository.findOneByEmail(userDto.getEmail());

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

}
