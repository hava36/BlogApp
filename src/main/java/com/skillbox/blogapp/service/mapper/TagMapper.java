package com.skillbox.blogapp.service.mapper;

import com.skillbox.blogapp.model.dto.TagDto;
import com.skillbox.blogapp.model.entity.Tag;
import java.util.Set;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDto}.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDto, Tag> {

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<TagDto> toDtoIdSet(Set<Tag> tag);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TagDto toDtoId(Tag tag);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    TagDto toDto(Tag tag);

}
