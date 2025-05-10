package Auxiliary.Enums.Domain;

public enum UpdateNewPlannedDateTime {
    MIDDLE (2), // Between the actual planned date and end date
    AFTER (3),
    FAILED (4),
    SUCCESS(5); // Before the actual planned date and end date
    private final int value;
    UpdateNewPlannedDateTime(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

}
