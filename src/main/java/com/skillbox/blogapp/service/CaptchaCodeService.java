package com.skillbox.blogapp.service;

import com.skillbox.blogapp.model.dto.CaptchaCodeDto;
import com.skillbox.blogapp.model.entity.CaptchaCode;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CaptchaCode}.
 */
public interface CaptchaCodeService {

    /**
     * Save a captchaCode.
     *
     * @param captchaCodeDto the entity to save.
     * @return the persisted entity.
     */
    CaptchaCodeDto save(CaptchaCodeDto captchaCodeDto);

    /**
     * Partially updates a captchaCode.
     *
     * @param captchaCodeDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CaptchaCodeDto> partialUpdate(CaptchaCodeDto captchaCodeDto);

    /**
     * Get all the captchaCodes.
     *
     * @return the list of entities.
     */
    List<CaptchaCodeDto> findAll();

    /**
     * Get the "id" captchaCode.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CaptchaCodeDto> findOne(Integer id);

    /**
     * Delete the "id" captchaCode.
     *
     * @param id the id of the entity.
     */
    void delete(Integer id);
}
