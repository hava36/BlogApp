package com.skillbox.blogapp.service;

import com.skillbox.blogapp.model.dto.GlobalSettingDto;
import com.skillbox.blogapp.model.entity.GlobalSetting;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link GlobalSetting}.
 */
public interface GlobalSettingService {

    /**
     * Save a globalSetting.
     *
     * @param globalSettingDto the entity to save.
     * @return the persisted entity.
     */
    GlobalSettingDto save(GlobalSettingDto globalSettingDto);

    /**
     * Partially updates a globalSetting.
     *
     * @param globalSettingDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GlobalSettingDto> partialUpdate(GlobalSettingDto globalSettingDto);

    /**
     * Get all the globalSettings.
     *
     * @return the list of entities.
     */
    List<GlobalSettingDto> findAll();

    /**
     * Get the "id" globalSetting.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GlobalSettingDto> findOne(Integer id);

    /**
     * Delete the "id" globalSetting.
     *
     * @param id the id of the entity.
     */
    void delete(Integer id);
}
