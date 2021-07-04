package com.skillbox.blogapp.web.rest;

import com.skillbox.blogapp.service.dto.auth.login.response.LoginResponseFail;
import com.skillbox.blogapp.service.exception.ErrorCredentialsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionController {

    @ExceptionHandler(ErrorCredentialsException.class)
    public ResponseEntity<LoginResponseFail> loginFail() {
        return ResponseEntity.ok(new LoginResponseFail());
    }

}
