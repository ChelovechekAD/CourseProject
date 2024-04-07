package it.academy.enums;

public enum RoleEnum {
    DEFAULT_USER("Default user"),
    WORKER("Worker"),
    MODERATOR("Moderator"),
    ADMIN("Admin");

    private final String displayName;

    RoleEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
