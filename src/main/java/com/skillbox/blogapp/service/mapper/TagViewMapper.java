package com.skillbox.blogapp.service.mapper;

import com.skillbox.blogapp.model.dto.TagDto;
import com.skillbox.blogapp.model.entity.Tag;
import com.skillbox.blogapp.model.entity.TagView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDto}.
 */
@Mapper(componentModel = "spring")
public interface TagViewMapper extends EntityMapper<TagDto, TagView> {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "weight", source = "weight")
    TagDto toDto(TagView tag);

}
