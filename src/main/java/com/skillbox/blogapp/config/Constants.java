package com.skillbox.blogapp.config;

import static com.skillbox.blogapp.model.entity.enums.ModerationStatus.ACCEPTED;
import static com.skillbox.blogapp.model.entity.enums.ModerationStatus.DECLINED;
import static com.skillbox.blogapp.model.entity.enums.ModerationStatus.NEW;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

/**
 * Application constants.
 */
@Component
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9]).{8,15}$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "ru";

    public static final String IS_ACTIVE_FIELD_NAME = "isActive";
    public static final String STATUSES_FIELD_NAME = "statuses";

    public static final Map<String, Sort> MODE_SORTING_SETTINGS = Map.of("best", Sort.by(Direction.DESC, "likeCount"),
        "popular", Sort.by(Direction.DESC, "commentCount"),
        "recent", Sort.by(Direction.DESC, "time"),
        "early", Sort.by(Direction.ASC, "time"));

    public static final Map<String, Map<String, Object>> MY_POSTS_STATUS_PARAMETERS = Map.of(
        "inactive", Map.of(IS_ACTIVE_FIELD_NAME, 0, STATUSES_FIELD_NAME, List.of(NEW.name(), DECLINED.name(), ACCEPTED.name())),
        "declined", Map.of(IS_ACTIVE_FIELD_NAME, 1, STATUSES_FIELD_NAME, List.of(DECLINED.name())),
        "pending", Map.of(IS_ACTIVE_FIELD_NAME, 1, STATUSES_FIELD_NAME, List.of(NEW.name())),
        "published", Map.of(IS_ACTIVE_FIELD_NAME, 1, STATUSES_FIELD_NAME, List.of(ACCEPTED.name()))
    );

    private Constants() {
    }
}
