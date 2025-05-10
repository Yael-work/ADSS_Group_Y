package Auxiliary.Enums.Domain;

public enum UpdateNewPlannedDateTime {
    BEFORE (1), //Before the actual planned date and end date
    MIDDLE (2), // Between the actual planned date and end date
    AFTER (3),
    FAILED (4); // After the actual planned date and end date
    private final int value;
    UpdateNewPlannedDateTime(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

}
