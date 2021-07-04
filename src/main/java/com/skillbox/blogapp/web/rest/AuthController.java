package com.skillbox.blogapp.web.rest;

import com.skillbox.blogapp.model.response.RegistrationResponse;
import com.skillbox.blogapp.service.CaptchaCodeService;
import com.skillbox.blogapp.service.UserService;
import com.skillbox.blogapp.service.dto.AuthResponseDto;
import com.skillbox.blogapp.service.dto.CaptchaCodeDto;
import com.skillbox.blogapp.service.dto.UserDto;
import com.skillbox.blogapp.service.dto.auth.login.request.LoginRequest;
import com.skillbox.blogapp.service.dto.auth.login.response.LoginResponseSuccess;
import com.skillbox.blogapp.service.dto.auth.logout.LogoutResponse;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CaptchaCodeService captchaCodeService;
    private final UserService userService;

    public AuthController(CaptchaCodeService captchaCodeService, UserService userService) {
        this.captchaCodeService = captchaCodeService;
        this.userService = userService;
    }

    @GetMapping("/check")
    public ResponseEntity<AuthResponseDto> check() {
        return new ResponseEntity<>(new AuthResponseDto(), HttpStatus.OK);
    }

    @GetMapping("/captcha")
    public ResponseEntity<CaptchaCodeDto> captcha() {
        var optionalCaptcha = captchaCodeService.generate();
        return new ResponseEntity<>(optionalCaptcha.orElseThrow(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.register(userDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseSuccess> login(@RequestBody LoginRequest loginRequest) {
        Optional<LoginResponseSuccess> optionalLoginResponse = userService.login(loginRequest);
        if (optionalLoginResponse.isPresent()) {
            return ResponseEntity.ok(optionalLoginResponse.get());
        }
        return ResponseEntity.ok(null);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@RequestHeader(name = "sessionId") String sessionId) {
        userService.logout(sessionId);
        return ResponseEntity.ok(new LogoutResponse());
    }

}
