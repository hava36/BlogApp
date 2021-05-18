package com.skillbox.blogapp.service.mapper;

import com.skillbox.blogapp.model.dto.GlobalSettingDto;
import com.skillbox.blogapp.model.dto.SingletonSettingDto;
import com.skillbox.blogapp.model.entity.GlobalSetting;
import com.skillbox.blogapp.service.mapper.handler.GlobalSettingConvertHandler;
import com.skillbox.blogapp.service.mapper.handler.impl.GlobalSettingConvertBooleanHandler;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link GlobalSetting} and its DTO {@link SingletonSettingDto}.
 */
@Mapper(componentModel = "spring")
public interface GlobalSettingMapper extends EntityMapper<GlobalSettingDto, GlobalSetting> {

    default SingletonSettingDto toSingleDtoFromList(
        List<GlobalSetting> entityList) {

        GlobalSettingConvertHandler<Boolean, String> booleanConvertHandler
            = new GlobalSettingConvertBooleanHandler();

        Map<String, GlobalSettingConvertHandler<Boolean, String>> booleanConvertHandlerMap =
            Map.of("MULTIUSER_MODE", booleanConvertHandler,
                "POST_PREMODERATION", booleanConvertHandler,
                "STATISTICS_IS_PUBLIC", booleanConvertHandler);

        Map<String, String> codeValues = entityList.stream()
            .collect(Collectors.toMap(GlobalSetting::getCode, GlobalSetting::getValue));

        return new SingletonSettingDto(
            booleanConvertHandlerMap.get("MULTIUSER_MODE")
                .parse(codeValues.get("MULTIUSER_MODE")),
            booleanConvertHandlerMap.get("POST_PREMODERATION")
                .parse(codeValues.get("POST_PREMODERATION")),
            booleanConvertHandlerMap.get("STATISTICS_IS_PUBLIC")
                .parse(codeValues.get("STATISTICS_IS_PUBLIC")));

    }

}
