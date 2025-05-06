package Auxiliary.Enums.Models;

public enum visitSourceEnum {
    OVERLOAD(1),
    FAILED(2),
    SUCCESS(3);

    private final int value;

    visitSourceEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
