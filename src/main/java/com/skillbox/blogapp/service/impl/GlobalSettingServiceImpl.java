package com.skillbox.blogapp.service.impl;

import com.skillbox.blogapp.model.dto.SingletonSettingDto;
import com.skillbox.blogapp.model.entity.GlobalSetting;
import com.skillbox.blogapp.repository.GlobalSettingRepository;
import com.skillbox.blogapp.service.GlobalSettingService;
import com.skillbox.blogapp.service.mapper.GlobalSettingMapper;
import java.util.List;
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

    private final GlobalSettingRepository globalSettingRepository;

    private final GlobalSettingMapper globalSettingMapper;

    public GlobalSettingServiceImpl(GlobalSettingRepository globalSettingRepository,
        GlobalSettingMapper globalSettingMapper) {
        this.globalSettingRepository = globalSettingRepository;
        this.globalSettingMapper = globalSettingMapper;
    }

    @Override
    public SingletonSettingDto save(SingletonSettingDto singletonSettingDto) {
        log.debug("Request to save GlobalSetting : {}", singletonSettingDto);
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public SingletonSettingDto findAll() {
        log.debug("Request to get all GlobalSettings");
        List<GlobalSetting> settings = globalSettingRepository.findAll();
        return globalSettingMapper.toSingleDtoFromList(settings);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete GlobalSetting : {}", id);
        globalSettingRepository.deleteById(id);
    }
}
