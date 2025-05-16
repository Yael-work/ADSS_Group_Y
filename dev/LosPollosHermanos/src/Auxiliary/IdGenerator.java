package Auxiliary;

public class IdGenerator {

    public static int manager_counter = 0;
    public static int cashier_counter = 0;
    public static int HR_counter = 0;
    public static int stocker_counter = 0;
    public static int security_counter = 0;
    public static int cleaner_counter = 0;
    public static int employee_counter = 0;
    public static int shift_counter = 0;
    public static int ARole_counter = 0;

    public static synchronized int getNextIdManager() {
        return ++manager_counter;
    }

    public static synchronized int getNextIdCashier() {
        return ++cashier_counter;
    }

    public static synchronized int getNextIdHR() {
        return ++HR_counter;
    }

    public static synchronized int getNextIdStocker() {
        return ++stocker_counter;
    }

    public static synchronized int getNextIdSecurity() {
        return ++security_counter;
    }

    public static synchronized int getNextIdCleaner() {
        return ++cleaner_counter;
    }

    public static synchronized int getNextIdEmployee() {
        return ++employee_counter;
    }

    public static synchronized int getNextIdShift() {
        return ++shift_counter;
    }

    public static synchronized int getNextIdARole() {
        return ++ARole_counter;
    }
}
