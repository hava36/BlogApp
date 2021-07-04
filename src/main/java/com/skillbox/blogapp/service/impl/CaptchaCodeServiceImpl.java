package com.skillbox.blogapp.service.impl;

import com.github.cage.Cage;
import com.github.cage.YCage;
import com.skillbox.blogapp.model.entity.CaptchaCode;
import com.skillbox.blogapp.repository.CaptchaCodeRepository;
import com.skillbox.blogapp.service.CaptchaCodeService;
import com.skillbox.blogapp.service.dto.CaptchaCodeDto;
import com.skillbox.blogapp.service.mapper.CaptchaCodeMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@EnableAsync
public class CaptchaCodeServiceImpl implements CaptchaCodeService {

    private final Logger log = LoggerFactory.getLogger(CaptchaCodeServiceImpl.class);

    private final CaptchaCodeRepository captchaCodeRepository;

    private final CaptchaCodeMapper captchaCodeMapper;

    @Value("${spring.captcha.expired_seconds}")
    private Integer expiredTimeInSeconds;

    public CaptchaCodeServiceImpl(CaptchaCodeRepository captchaCodeRepository,
        CaptchaCodeMapper captchaCodeMapper) {
        this.captchaCodeRepository = captchaCodeRepository;
        this.captchaCodeMapper = captchaCodeMapper;
    }

    @Override
    public Optional<CaptchaCodeDto> findValidOneBySecretCode(String code) {
        Optional<CaptchaCode> optionalCaptchaCode =
            captchaCodeRepository.findOneByCodeMoreThenTime(code, Instant.now().minus(expiredTimeInSeconds, ChronoUnit.SECONDS));
        return Optional.of(captchaCodeMapper.toDto(optionalCaptchaCode.orElse(new CaptchaCode())));
    }

    @Override
    public Optional<CaptchaCodeDto> generate() {

        Cage cage = new YCage();
        Instant time = Instant.now();
        String code = cage.getTokenGenerator().next();
        String secretCode = UUID.randomUUID().toString();
        String imageInBase64 = String.format("data:image/png;base64, %s",
            Base64.getEncoder().encodeToString(cage.draw(secretCode)));

        var captchaCode = new CaptchaCode(time, code, secretCode, imageInBase64);

        return Optional.ofNullable(captchaCodeMapper.toDto(captchaCodeRepository.save(captchaCode)));
    }

    @Override
    //@Scheduled(fixedDelay = 3600000)
    public void removeExpiredCaptcha() {
        log.debug("============>>>>>>>> removeExpiredCaptcha() is working");
        captchaCodeRepository.deleteAll(captchaCodeRepository.findCaptchaCodeByLessThenTime(Instant.now().minus(expiredTimeInSeconds, ChronoUnit.SECONDS)));
    }

}
