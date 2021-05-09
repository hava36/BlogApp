package com.skillbox.blogapp.model.dto;

import com.skillbox.blogapp.model.entity.GlobalSetting;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for the {@link GlobalSetting} entity.
 */
@Getter
@Setter
public class GlobalSettingDto implements Serializable {

    private Integer id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    @NotNull
    private String value;

}
