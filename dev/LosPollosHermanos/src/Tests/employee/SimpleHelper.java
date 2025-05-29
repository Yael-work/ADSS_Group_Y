package Tests.employee;

public class SimpleHelper {
    public static void showError(String message) {
        System.err.println("ERROR: " + message);
    }
    
    public static void showError(String message, Exception e) {
        System.err.println("ERROR: " + message);
        if (e != null) {
            e.printStackTrace();
        }
    }
}
