package com.skillbox.blogapp.service;

import com.skillbox.blogapp.model.entity.GlobalSetting;
import java.util.Map;

/**
 * Service Interface for managing {@link GlobalSetting}.
 */
public interface GlobalSettingService {

    /**
     * Get all the globalSettings.
     *
     * @return the list of entities.
     */
    Map<String, Object> findAll();

}
