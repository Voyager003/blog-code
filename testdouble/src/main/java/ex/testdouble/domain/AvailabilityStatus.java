package ex.testdouble.domain;

public enum AvailabilityStatus {
    AVAILABLE("Available"),
    RESERVED("Reserved");

    private final String displayName;

    AvailabilityStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
