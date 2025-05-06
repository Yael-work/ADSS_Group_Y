package Auxiliary;

public class GlobalConfig {
    private static final String DATABASE_CONFIG_URL = "hibernate.cfg.xml"; // default
    private static final String DATABASE_CONFIG_TESTING_URL = "hibernate-test.cfg.xml"; // testing only db
    private static boolean testMode = false;

    public static void setTestMode(boolean testing) {
        testMode = testing;
    }

    public static String getDatabaseConfigUrl() {
        return testMode ? DATABASE_CONFIG_TESTING_URL : DATABASE_CONFIG_URL;
    }
}
