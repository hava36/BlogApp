package com.skillbox.blogapp.service;

import com.skillbox.blogapp.service.dto.CaptchaCodeDto;
import java.util.Optional;

public interface CaptchaCodeService {

    Optional<CaptchaCodeDto> findValidOneBySecretCode(String code);

    Optional<CaptchaCodeDto> generate();

    void removeExpiredCaptcha();

}
