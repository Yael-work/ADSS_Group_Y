package Presentation.transport;

import auxiliary.Helper;

public class BranchesHandler {
    private static boolean addBranch() {
        String name = null;
        String address = null;
        String phone = null;
        try {
            System.out.println("Branch creation:");
            do {
                System.out.print("Enter Branch name: ");
                name = Helper.getUserInputString();
            } while (name == null || name.isEmpty());
            do {
                System.out.print("Enter Branch address: ");
                address = Helper.getUserInputString();
            } while (address == null || address.isEmpty());
            do {
                System.out.print("Enter Branch phone: ");
                phone = Helper.getUserInputString();
            } while (phone == null || phone.isEmpty());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }


        return true;
    }


    public static void handleBranches() {
        boolean exit = false;
        while (!exit) {
            int choice;
            System.out.println("Manage Branches:");
            System.out.println("1. Add Branch");
            System.out.println("2. Update Branch");
            System.out.println("3. Delete Branch");
            System.out.println("4. View Branch by ID");
            System.out.println("5. View Existing Branches IDs");
            System.out.println("6. Back to Main Menu");

            do {
                System.out.print("Please choose an option: ");
                choice = Helper.getUserInputInteger();
            } while (choice == Helper.UndefinedUserInput);

            switch (choice) {
                case 1:
                    addBranch();
                    break;
                case 2:
                    // Call method to update branch
                    break;
                case 3:
                    // Call method to delete branch
                    break;
                case 4:
                    // Call method to view branch by ID
                    break;
                case 5:
                    // Call method to view existing branches IDs
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
