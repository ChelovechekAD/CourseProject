package it.academy.enums;

public enum DiscountType {

    PERCENTAGE("Percentage"),
    FIXED("Fixed");

    private final String displayName;

    DiscountType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
