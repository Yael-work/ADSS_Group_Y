package Presentation.transport;

import Domain.transport.BranchesManager;
import auxiliary.Helper;

public class BranchesHandler {

    private static boolean addBranch() {
        String name = null;
        String address = null;
        String phone = null;
        try {
            Helper.println("Branch creation:");
            do {
                Helper.print("Enter Branch name: ");
                name = Helper.getUserInputString();
            } while (name == null || name.isEmpty());
            do {
                Helper.print("Enter Branch address: ");
                address = Helper.getUserInputString();
            } while (address == null || address.isEmpty());
            do {
                Helper.print("Enter Branch phone: ");
                phone = Helper.getUserInputString();
            } while (phone == null || phone.isEmpty());
        } catch (Exception e) {
            Helper.println("Error: " + e.getMessage());
            return false;
        }
        return BranchesManager.addBranch(name, address, phone);
    }


    public static void handleBranches() {
        boolean exit = false;
        while (!exit) {
            int choice;
            Helper.println("Manage Branches:");
            Helper.println("1. Add Branch");
            Helper.println("2. Update Branch");
            Helper.println("3. Delete Branch");
            Helper.println("4. View Branch by ID");
            Helper.println("5. View Existing Branches IDs");
            Helper.println("6. Back to Main Menu");

            do {
                Helper.print("Please choose an option: ");
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
                    Helper.println("Invalid choice. Please try again.");
            }
        }
    }
}
