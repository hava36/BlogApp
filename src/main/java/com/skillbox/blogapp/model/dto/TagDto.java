package com.skillbox.blogapp.model.dto;

import com.skillbox.blogapp.model.entity.Tag;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for the {@link Tag} entity.
 */
@Getter
@Setter
public class TagDto implements Serializable {

    private Integer id;

    @NotNull
    private String name;

}