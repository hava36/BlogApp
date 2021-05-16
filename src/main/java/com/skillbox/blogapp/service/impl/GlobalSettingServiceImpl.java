package com.skillbox.blogapp.service.impl;

import com.skillbox.blogapp.model.dto.GlobalSettingDto;
import com.skillbox.blogapp.model.entity.GlobalSetting;
import com.skillbox.blogapp.repository.GlobalSettingRepository;
import com.skillbox.blogapp.service.GlobalSettingService;
import com.skillbox.blogapp.service.mapper.GlobalSettingMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
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
    public GlobalSettingDto save(GlobalSettingDto globalSettingDto) {
        log.debug("Request to save GlobalSetting : {}", globalSettingDto);
        var globalSetting = globalSettingMapper.toEntity(globalSettingDto);
        globalSetting = globalSettingRepository.save(globalSetting);
        return globalSettingMapper.toDto(globalSetting);
    }

    @Override
    public Optional<GlobalSettingDto> partialUpdate(GlobalSettingDto globalSettingDto) {
        log.debug("Request to partially update GlobalSetting : {}", globalSettingDto);

        return globalSettingRepository
            .findById(globalSettingDto.getId())
            .map(
                existingGlobalSetting -> {
                    globalSettingMapper.partialUpdate(existingGlobalSetting, globalSettingDto);
                    return existingGlobalSetting;
                }
            )
            .map(globalSettingRepository::save)
            .map(globalSettingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GlobalSettingDto> findAll() {
        log.debug("Request to get all GlobalSettings");
        return globalSettingRepository.findAll(Sort.by("code")).stream()
            .map(globalSettingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GlobalSettingDto> findOne(Integer id) {
        log.debug("Request to get GlobalSetting : {}", id);
        return globalSettingRepository.findById(id).map(globalSettingMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete GlobalSetting : {}", id);
        globalSettingRepository.deleteById(id);
    }
}
