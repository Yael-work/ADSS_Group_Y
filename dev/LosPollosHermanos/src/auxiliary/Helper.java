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

    public static double getUserInputDouble() {
        try {
            return new java.util.Scanner(System.in).nextDouble();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }
}
