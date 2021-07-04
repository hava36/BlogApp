package com.skillbox.blogapp.service.dto.auth.login.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest implements Serializable {

    @JsonProperty("e_mail")
    private String email;

    private String password;

}
