package Presentation.transport;

import Domain.transport.BranchesManager;
import Models.DBModels.transport.Branch;
import Auxiliary.Helper;

import java.util.ArrayList;

public class BranchesHandler {

    private static void addBranch() {
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
        }
        if (!BranchesManager.addBranch(name, address, phone)) {
            Helper.showError("Error adding branch");
        }
    }

    private static void viewAllBranches() {
        try {
            ArrayList<Branch> lst = BranchesManager.viewExistingBranchesIDs();
            if (lst == null || lst.isEmpty()) {
                Helper.println("No branches found.");
                return;
            }
            Helper.println("Existing Branches:");
            for (int i = 0; i < BranchesManager.viewExistingBranchesIDs().size(); i++) {
                Helper.println("Branch ID: " + lst.get(i).getId() + ", Name: " + lst.get(i).getSiteName());
            }
        } catch (Exception e) {
            Helper.showError(e.getMessage(), Helper.getStringTraceOfException(e));
        }
    }

    private static void printBranchById() {
        try {
            int id;
            do {
                Helper.print("Enter Branch ID: ");
                id = Helper.getUserInputInteger();
            } while (id == Helper.UndefinedUserInput);
            Branch b = BranchesManager.getBranchById(id);
            if (b == null) {
                Helper.println("No branch found with ID: " + id);
                return;
            }
            Helper.println(b.toString());
        } catch (Exception e) {
            Helper.showError(e.getMessage(), Helper.getStringTraceOfException(e));
        }
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
            Helper.println("5. View Existing Branches");
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
                    printBranchById();
                    break;
                case 5:
                    viewAllBranches();
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
