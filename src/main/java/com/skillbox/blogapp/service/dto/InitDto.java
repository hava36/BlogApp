package com.skillbox.blogapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class InitDto {

    @Value("${spring.data.constant.title}")
    private String title;

    @Value("${spring.data.constant.subtitle}")
    private String subtitle;

    @Value("${spring.data.constant.phone}")
    private String phone;

    @Value("${spring.data.constant.email}")
    private String email;

    @Value("${spring.data.constant.copyright}")
    private String copyright;

    @Value("${spring.data.constant.copyrightFrom}")
    private String copyrightFrom;

}
