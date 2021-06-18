package com.skillbox.blogapp.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skillbox.blogapp.service.dto.TagDto;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;

@Getter
public class TagResponseList implements Serializable {

    private final Integer count;

    @JsonIgnoreProperties(value = {"id"})
    private final List<TagDto> tags;

    public TagResponseList(List<TagDto> tags) {
        this.count = tags.size();
        this.tags = tags;
    }

}
