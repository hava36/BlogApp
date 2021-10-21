package com.skillbox.blogapp.service.impl;

import com.skillbox.blogapp.model.entity.GlobalSetting;
import com.skillbox.blogapp.repository.GlobalSettingRepository;
import com.skillbox.blogapp.service.GlobalSettingService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GlobalSetting}.
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GlobalSettingServiceImpl implements GlobalSettingService {

    private static final String TRUE_VALUE = "YES";

    private final GlobalSettingRepository settingRepository;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Boolean> findAll() {
        log.debug("Request to get all GlobalSettings");
        List<GlobalSetting> settings = settingRepository.findAll();
        return settings.stream()
            .collect(Collectors.toMap(GlobalSetting::getCode, globalSetting ->
                SettingType.valueOf(globalSetting.getValue()).getValueOnFront()
            ));
    }

    @Override
    public Map<String, Boolean> updateSettings(Map<String, Boolean> settingsMap) {
        List<GlobalSetting> settingsList = settingRepository.findAll();
        settingsList.forEach(globalSetting -> {
            String code = globalSetting.getCode();
            if (settingsMap.containsKey(code)) {
                Boolean valueOnFront = settingsMap.get(code);
                globalSetting.setValue(SettingType.findType(valueOnFront).name());
            }
        });
        settingRepository.saveAll(settingsList);
        return findAll();
    }

    enum SettingType {
        YES(true),
        NO(false);

        private final boolean valueOnFront;

        SettingType(boolean value) {
            this.valueOnFront = value;
        }

        public boolean getValueOnFront() {
            return valueOnFront;
        }

        public static SettingType findType(boolean frontValue) {

            for (SettingType value : values()) {
                if (value.getValueOnFront() == frontValue) {
                    return value;
                }
            }

            return NO;

        }

    }


}
