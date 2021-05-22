package com.skillbox.blogapp.service.impl;

import com.skillbox.blogapp.config.Constants;
import com.skillbox.blogapp.model.entity.GlobalSetting;
import com.skillbox.blogapp.service.GlobalSettingService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GlobalSetting}.
 */
@Service
@Transactional
public class GlobalSettingServiceImpl implements GlobalSettingService {

    private final Logger log = LoggerFactory.getLogger(GlobalSettingServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> findAll() {
        log.debug("Request to get all GlobalSettings");
        return Map.of(
            "MULTIUSER_MODE", Constants.MULTIUSER_MODE,
            "STATISTICS_IS_PUBLIC", Constants.STATISTICS_IS_PUBLIC,
            "POST_PREMODERATION", Constants.POST_PREMODERATION);
    }


}
