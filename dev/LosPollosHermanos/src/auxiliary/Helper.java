package auxiliary;

public class Helper {
    public final static int UndefinedUserInput = -1;

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
}
