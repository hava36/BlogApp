package com.skillbox.blogapp.web.rest;

import com.skillbox.blogapp.model.dto.AuthResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    @GetMapping("/check")
    public ResponseEntity<AuthResponseDto> check() {
        return new ResponseEntity<>(new AuthResponseDto(), HttpStatus.OK);
    }

}
