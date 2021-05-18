package com.skillbox.blogapp.model.dto;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * A GlobalSetting.
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class GlobalSettingDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String code;

    private String name;

    private String value;

}
