package com.skillbox.blogapp.service.dto.auth.login.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseSuccessUser {

    private Integer id;
    private String name;
    private String photo;
    private String email;
    private Boolean moderation;
    private Long moderationCount;
    private Boolean settings;

}
