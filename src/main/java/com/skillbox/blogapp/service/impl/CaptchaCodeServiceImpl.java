package com.skillbox.blogapp.service.impl;

import com.skillbox.blogapp.model.dto.CaptchaCodeDto;
import com.skillbox.blogapp.model.entity.CaptchaCode;
import com.skillbox.blogapp.repository.CaptchaCodeRepository;
import com.skillbox.blogapp.service.CaptchaCodeService;
import com.skillbox.blogapp.service.mapper.CaptchaCodeMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CaptchaCode}.
 */
@Service
@Transactional
public class CaptchaCodeServiceImpl implements CaptchaCodeService {

    private final CaptchaCodeRepository captchaCodeRepository;

    private final CaptchaCodeMapper captchaCodeMapper;

    @Value("${spring.captcha.expired_seconds}")
    private Integer expiredTime;

    public CaptchaCodeServiceImpl(CaptchaCodeRepository captchaCodeRepository,
        CaptchaCodeMapper captchaCodeMapper) {
        this.captchaCodeRepository = captchaCodeRepository;
        this.captchaCodeMapper = captchaCodeMapper;
    }

    @Override
    public CaptchaCodeDto generate() {
        captchaCodeRepository.deleteAll(captchaCodeRepository.findCaptchaCodeByLessThenTime(Instant.now().minus(expiredTime, ChronoUnit.SECONDS)));
        var captchaCode = new CaptchaCode(UUID.randomUUID().toString());
        return captchaCodeMapper.toDto(captchaCodeRepository.save(captchaCode));
    }

}
