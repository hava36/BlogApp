package com.skillbox.blogapp.service;

import com.skillbox.blogapp.model.dto.SingletonSettingDto;
import com.skillbox.blogapp.model.entity.GlobalSetting;

/**
 * Service Interface for managing {@link GlobalSetting}.
 */
public interface GlobalSettingService {

    /**
     * Save a globalSetting.
     *
     * @param singletonSettingDto the entity to save.
     * @return the persisted entity.
     */
    SingletonSettingDto save(SingletonSettingDto singletonSettingDto);

    /**
     * Get all the globalSettings.
     *
     * @return the list of entities.
     */
    SingletonSettingDto findAll();

    /**
     * Delete the "id" globalSetting.
     *
     * @param id the id of the entity.
     */
    void delete(Integer id);

}
