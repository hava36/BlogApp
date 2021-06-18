package com.skillbox.blogapp.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {

    @JsonIgnore
    private UserDto userDto;

    private boolean result;

}
