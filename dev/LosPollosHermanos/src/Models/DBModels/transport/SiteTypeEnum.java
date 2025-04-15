package Models.DBModels.transport;

/**
 * Enum representing different types of sites.
 */
public enum SiteTypeEnum {
    BRANCH(1),
    SUPPLIER(2);

    private final int value;

    SiteTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}