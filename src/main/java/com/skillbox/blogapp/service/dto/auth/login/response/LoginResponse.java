package com.skillbox.blogapp.service.dto.auth.login.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class LoginResponse {

    private LoginResponseUser user;
    private Boolean result;

    public LoginResponse(LoginResponseUser user, Boolean result) {
        this.user = user;
        this.result = result;
    }

    public LoginResponse(Boolean result) {
        this.result = result;
    }
}
