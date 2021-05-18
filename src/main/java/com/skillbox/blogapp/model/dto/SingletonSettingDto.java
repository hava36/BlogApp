package com.skillbox.blogapp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillbox.blogapp.model.entity.GlobalSetting;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO for the {@link GlobalSetting} entity.
 */
@Getter
@Setter
@NoArgsConstructor
public class SingletonSettingDto implements Serializable {

    public SingletonSettingDto(boolean multiUserMode, boolean postPreModeration,
        boolean statisticsIsPublic) {
        this.multiUserMode = multiUserMode;
        this.postPreModeration = postPreModeration;
        this.statisticsIsPublic = statisticsIsPublic;
    }

    @JsonProperty("MULTIUSER_MODE")
    private boolean multiUserMode;

    @JsonProperty("POST_PREMODERATION")
    private boolean postPreModeration;

    @JsonProperty("STATISTICS_IS_PUBLIC")
    private boolean statisticsIsPublic;

}
