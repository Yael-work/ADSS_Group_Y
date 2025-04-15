package Presentation.transport;

import auxiliary.Helper;

public class TransportPresentation {


    private static void printInstructions() {
        System.out.println("Welcome to the Transport Management System!");
        System.out.println("Please choose an option:");
        System.out.println("1. Manage Transport Areas");
        System.out.println("2. Manage Transports");
        System.out.println("3. Manage Trucks");
        System.out.println("4. Manage Drivers");
        System.out.println("5. Manage Branches");
        System.out.println("6. Exit");
    }

    /**
     * Handles user input and calls the appropriate method based on the choice.
     *
     * @param choice the user's choice
     * @return true if the user wants to continue, false if they want to exit
     */
    private static boolean handleUserInput(int choice) {
        switch (choice) {
            case 1:
                // Call method to manage transport areas
                break;
            case 2:
                // Call method to manage transports
                break;
            case 3:
                // Call method to manage trucks
                break;
            case 4:
                // Call method to manage drivers
                break;
            case 5:
                BranchesHandler.handleBranches();
                break;
            case 6:
                System.out.println("Exiting the system. Goodbye!");
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
        return true;
    }

    public static void TransportPresentationHandler() {
        boolean exit = false;
        while (!exit) {
            printInstructions();
            System.out.print("Enter your choice: ");
            try {
                int choice = Helper.getUserInputInteger();
                exit = !handleUserInput(choice);
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}
