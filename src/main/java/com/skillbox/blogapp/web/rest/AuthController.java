package com.skillbox.blogapp.web.rest;

import com.skillbox.blogapp.model.response.RegistrationResponse;
import com.skillbox.blogapp.service.CaptchaCodeService;
import com.skillbox.blogapp.service.UserService;
import com.skillbox.blogapp.service.dto.CaptchaCodeDto;
import com.skillbox.blogapp.service.dto.UserDto;
import com.skillbox.blogapp.service.dto.auth.login.request.LoginRequest;
import com.skillbox.blogapp.service.dto.auth.login.response.LoginResponse;
import com.skillbox.blogapp.service.dto.auth.logout.LogoutResponse;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<LoginResponse> check() {

        LoginResponse response = userService.check().orElse(new LoginResponse(false));

        return ResponseEntity.ok(response);
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<LoginResponse> optionalLoginResponse = userService.login(loginRequest);
        return optionalLoginResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(null));
    }

    @GetMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request, response);
        return ResponseEntity.ok(new LogoutResponse());
    }

}
