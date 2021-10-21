package com.skillbox.blogapp.model.entity.enums;

public enum Permission {

    USER("user:write"),
    MODERATE("user:moderate");

    private final String value;

    Permission(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
