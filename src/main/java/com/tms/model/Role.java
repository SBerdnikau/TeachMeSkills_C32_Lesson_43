package com.tms.model;

public enum Role {
    USER("The average user"),
    ADMIN("Administrator"),
    MODERATOR("Moderator");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
