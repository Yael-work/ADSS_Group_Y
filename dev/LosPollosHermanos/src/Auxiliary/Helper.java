package Auxiliary;

import Models.DBModels.BaseModel;
import java.util.List;

public class Helper {
    public final static int UndefinedUserInput = -1;
    public final static int UndefinedId = -1;

    /**
     * This method is used to get user input from the console.
     *
     * @return - 1 if the input is invalid, otherwise returns the user input as an integer.
     */
    public static int getUserInputInteger() {
        try {
            return new java.util.Scanner(System.in).nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            return UndefinedUserInput;
        }
    }

    /**
     * This method is used to get user input from the console.
     *
     * @return - the user input as a string or null if the input is invalid.
     */
    public static String getUserInputString() {
        try {
            return new java.util.Scanner(System.in).nextLine();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a string.");
            return null;
        }
    }

    /**
     * This method is used to get user double from the console.
     *
     * @return -1 if the input is invalid, otherwise returns the user input as a double.
     */
    public static double getUserInputDouble() {
        try {
            return new java.util.Scanner(System.in).nextDouble();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            return UndefinedUserInput;
        }
    }

    /**
     * This method is used to show an error message to the user.
     *
     * @param message   The error message to be displayed.
     * @param trace     The stack trace of the error.
     * @param logToFile Whether to log the error message to a file or not.
     */
    public static void showError(String message, String trace, boolean logToFile) {
        String err = String.format("--------- System Error message: {%s} ---------\n" + "%s" + "\n" + "--------- END TRACE ---------",
                message, trace != null ? trace : "");
        System.out.println(err);
        if (logToFile) {
            //todo implement logging to file
        }
    }

    /**
     * This method is used to show an error message to the user.
     *
     * @param message The error message to be displayed.
     * @param trace   The stack trace of the error.
     */
    public static void showError(String message, String trace) {
        showError(message, trace, true);
    }

    /**
     * This method is used to show an error message to the user.
     *
     * @param message The error message to be displayed.
     * @param e       The exception that caused the error.
     */
    public static void showError(String message, Exception e) {
        showError(message, getStringTraceOfException(e), true);
    }

    /**
     * This method is used to show an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public static void showError(String message) {
        showError(message, null, true);
    }

    /**
     * This method is used to show an error message to the user.
     *
     * @param message   - The error message to be displayed.
     * @param logToFile - Whether to log the error message to a file or not.
     */
    public static void print(String message, boolean logToFile) {
        System.out.print(message);
        if (logToFile) {
            //todo implement logging to file
        }
    }

    /**
     * This method is used to show an error message to the user.
     *
     * @param message - The error message to be displayed.
     */
    public static void print(String message) {
        print(message, true);
    }

    /**
     * This method is used to show an error message to the user.
     *
     * @param message   - The error message to be displayed.
     * @param logToFile - Whether to log the error message to a file or not.
     */
    public static void println(String message, boolean logToFile) {
        System.out.println(message);
        if (logToFile) {
            //todo implement logging to file
        }
    }

    /**
     * This method is used to show an error message to the user.
     *
     * @param message - The error message to be displayed.
     */
    public static void println(String message) {
        println(message, true);
    }

    /**
     * This method is used to get the stack trace of an exception as a string.
     *
     * @param e - The exception to get the stack trace from.
     * @return - The stack trace of the exception as a string.
     */
    public static String getStringTraceOfException(Exception e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * This method is used to check if a given ID is valid.
     *
     * @param areas   - The list of areas to check against.
     * @param checkId - The ID to check.
     * @return - true if the ID is valid, false otherwise.
     */
    public static boolean isValidId(List<? extends BaseModel> areas, int checkId) {
        return checkId > 0 && areas.stream().anyMatch(a -> a.getId() == checkId);
    }
}
