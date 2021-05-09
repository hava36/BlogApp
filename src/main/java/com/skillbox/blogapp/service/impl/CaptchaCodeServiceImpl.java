package com.skillbox.blogapp.service.impl;

import com.skillbox.blogapp.model.dto.CaptchaCodeDto;
import com.skillbox.blogapp.model.entity.CaptchaCode;
import com.skillbox.blogapp.repository.CaptchaCodeRepository;
import com.skillbox.blogapp.service.CaptchaCodeService;
import com.skillbox.blogapp.service.mapper.CaptchaCodeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CaptchaCode}.
 */
@Service
@Transactional
public class CaptchaCodeServiceImpl implements CaptchaCodeService {

    private final Logger log = LoggerFactory.getLogger(CaptchaCodeServiceImpl.class);

    private final CaptchaCodeRepository captchaCodeRepository;

    private final CaptchaCodeMapper captchaCodeMapper;

    public CaptchaCodeServiceImpl(CaptchaCodeRepository captchaCodeRepository,
        CaptchaCodeMapper captchaCodeMapper) {
        this.captchaCodeRepository = captchaCodeRepository;
        this.captchaCodeMapper = captchaCodeMapper;
    }

    @Override
    public CaptchaCodeDto save(CaptchaCodeDto captchaCodeDto) {
        log.debug("Request to save CaptchaCode : {}", captchaCodeDto);
        var captchaCode = captchaCodeMapper.toEntity(captchaCodeDto);
        captchaCode = captchaCodeRepository.save(captchaCode);
        return captchaCodeMapper.toDto(captchaCode);
    }

    @Override
    public Optional<CaptchaCodeDto> partialUpdate(CaptchaCodeDto captchaCodeDto) {
        log.debug("Request to partially update CaptchaCode : {}", captchaCodeDto);

        return captchaCodeRepository
            .findById(captchaCodeDto.getId())
            .map(
                existingCaptchaCode -> {
                    captchaCodeMapper.partialUpdate(existingCaptchaCode, captchaCodeDto);
                    return existingCaptchaCode;
                }
            )
            .map(captchaCodeRepository::save)
            .map(captchaCodeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CaptchaCodeDto> findAll() {
        log.debug("Request to get all CaptchaCodes");
        return captchaCodeRepository.findAll().stream().map(captchaCodeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CaptchaCodeDto> findOne(Integer id) {
        log.debug("Request to get CaptchaCode : {}", id);
        return captchaCodeRepository.findById(id).map(captchaCodeMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete CaptchaCode : {}", id);
        captchaCodeRepository.deleteById(id);
    }
}
