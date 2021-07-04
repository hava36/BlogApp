package com.skillbox.blogapp.service.dto.auth.login.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginResponseSuccess {

    private Boolean result = true;
    private final String sessionId;
    private final LoginResponseSuccessUser user;

}
