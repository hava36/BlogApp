package com.skillbox.blogapp.web.rest;

import com.skillbox.blogapp.service.dto.auth.login.response.FailLoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<FailLoginResponse> loginFail() {
        return ResponseEntity.ok(new FailLoginResponse());
    }

}
