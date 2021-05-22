package com.skillbox.blogapp.config;

import org.springframework.stereotype.Component;

/**
 * Application constants.
 */
@Component
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "ru";

    public static final boolean MULTIUSER_MODE = false;
    public static final boolean POST_PREMODERATION = true;
    public static final boolean STATISTICS_IS_PUBLIC = true;

    private Constants() {
    }
}
