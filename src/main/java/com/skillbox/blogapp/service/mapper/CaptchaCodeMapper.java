package com.skillbox.blogapp.service.mapper;

import com.skillbox.blogapp.model.entity.CaptchaCode;
import com.skillbox.blogapp.service.dto.CaptchaCodeDto;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link CaptchaCode} and its DTO {@link CaptchaCodeDto}.
 */
@Mapper(componentModel = "spring")
public interface CaptchaCodeMapper extends EntityMapper<CaptchaCodeDto, CaptchaCode> {


}
