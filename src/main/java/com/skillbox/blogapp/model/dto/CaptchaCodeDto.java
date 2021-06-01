package com.skillbox.blogapp.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillbox.blogapp.model.entity.CaptchaCode;
import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for the {@link CaptchaCode} entity.
 */
@Getter
@Setter
public class CaptchaCodeDto implements Serializable {

    @JsonIgnore
    private Integer id;

    @JsonIgnore
    private Instant time;

    @JsonIgnore
    private String code;

    @JsonProperty("secret")
    private String secretCode;

    @JsonProperty("image")
    private String imageInBase64;

}
