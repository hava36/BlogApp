package com.skillbox.blogapp.service.mapper;

import com.skillbox.blogapp.model.dto.GlobalSettingDto;
import com.skillbox.blogapp.model.entity.GlobalSetting;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link GlobalSetting} and its DTO {@link GlobalSettingDto}.
 */
@Mapper(componentModel = "spring")
public interface GlobalSettingMapper extends EntityMapper<GlobalSettingDto, GlobalSetting> {

}
