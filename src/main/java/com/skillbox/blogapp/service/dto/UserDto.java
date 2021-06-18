package com.skillbox.blogapp.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillbox.blogapp.model.entity.User;
import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for the {@link User} entity.
 */
@Getter
@Setter
public class UserDto implements Serializable {

    private Integer id;

    @NotNull
    private Integer isModerator;

    private Instant regTime;

    private String name;

    @JsonProperty("e_mail")
    private String email;

    private String password;

    private String code;

    private String photo;

    @JsonProperty("captcha")
    private String captchaCode;

    @JsonProperty("captcha_secret")
    private String captchaSecretCode;

    public UserDto() {
        this.isModerator = 0;
    }
}
