package Presentation.transport;

import Auxiliary.Helper;
import Models.DBModels.transport.Branch;
import Models.DBModels.transport.TransportArea;
import Domain.transport.TransportAreasManager;

import java.util.ArrayList;

public class TransportAreaHandler {
    private static boolean addTransportArea() {
        String areaName = "";
        ArrayList<Integer> suppliersIds = null; // Need to be initialized
        ArrayList<Integer> branchesIds = new ArrayList<>();
        try {
            Helper.println("Transport Area creation:");
            do {
                Helper.println("Enter Transport Area name: ");
                areaName = Helper.getUserInputString();
            } while (areaName == null || areaName.isEmpty());
            ArrayList<Branch> b = BranchesHandler.viewAllBranches();
            if (b == null || b.isEmpty()) {
                return false;
            } else {
                Helper.println("Choose branches you want to add: ");
                for (Branch branch : b) {
                    Helper.println("Branch ID: " + branch.getId() + ", Name: " + branch.getSiteName());
                }
                int id = 0;
                do {
                    Helper.println("Enter Id: ");
                    id = Helper.getUserInputInteger();
                    boolean f = false;
                    for (Branch branch : b) {
                        if (branch.getId() == id) {
                            branchesIds.add(branch.getId());
                            b.remove(branch);
                            f = true;
                            break;
                        }
                    }
                    if (!f) {
                        Helper.println("Invalid id. try again.");
                    } else {
                        int choice = 0;
                        if (!b.isEmpty()) {
                            do {
                                Helper.println("You want to add more? press 1 for yes or 2 for no.");
                                choice = Helper.getUserInputInteger();
                                if (choice == 1 || choice == 2) {
                                    break;
                                } else {
                                    Helper.println("Error, please try again. ");
                                }
                            } while (true);
                            if (choice == 2) {
                                break;
                            }
                        }
                        else{
                            Helper.println("No more branches available.");
                            break;
                        }
                    }
                } while (true);
            }
        } catch (Exception e) {
            Helper.showError("Error: " + e.getMessage());
            return false;
        }
        boolean f = TransportAreasManager.addNewTransportArea(areaName, branchesIds, suppliersIds);
        if (!f) {
            Helper.showError("Transport area with this name already exists");
            return false;
        }
        return true;
    }


    private static ArrayList<TransportArea> printAllTransportAreas() {
        ArrayList<TransportArea> allAreas = TransportAreasManager.getAllTransportAreas();
        if (allAreas == null || allAreas.isEmpty()) {
            Helper.println("No transport areas available.");
            return null;
        }
        Helper.println("All Transport Areas:");
        for (TransportArea area : allAreas) {
            Helper.println("ID: " + area.getId() + ", Name: " + area.getAreaName());
        }
        return allAreas;
    }

    /*
     * This method is used to print a transport area by its ID.
     * It prompts the user to enter the ID of the transport area they want to view.
     * If the ID is valid, it retrieves and prints the transport area details.
     */
    private static boolean printTransportAreaById() {
        try {
            Helper.println("Enter the transport area you want to view:");
            int id = Helper.getUserInputInteger();
            TransportArea area = TransportAreasManager.getTransportAreaById(id);
            if (area != null) {
                Helper.println("ID: " + area.getId() + ", Name: " + area.getAreaName());
                return true;
            } else {
                Helper.println("No transport area found with the given ID.");
            }
        } catch (Exception e) {
            Helper.showError("Error: " + e.getMessage(), e);
        }
        return false;
    }

    private static boolean updateTransportArea() {
        try {
            Helper.println("Transport Area update:");
            Helper.println("Which transport area do you want to update?");
            ArrayList<TransportArea> areas = printAllTransportAreas();
            if (areas != null && !areas.isEmpty()) {
                int id = Helper.getUserInputInteger();
                while (!Helper.isValidId(areas, id)) {
                    Helper.println("Invalid ID. Please enter a valid transport area ID that exists in the system.");
                    id = Helper.getUserInputInteger();
                }
            }
        } catch (Exception e) {
            Helper.showError("Error: " + e.getMessage(), e);
        }
        return false;
    }

    /*
     * This method is used to delete a transport area.
     * It prompts the user to enter the ID of the transport area they want to delete.
     * If the ID is valid, it deletes the transport area and prints a success message.
     */
    private static boolean deleteTransportArea() {
        try {
            Helper.println("Enter the transport area you want to delete:");
            int id = Helper.getUserInputInteger();
            boolean result = TransportAreasManager.removeTransportArea(id);
            if (result) {
                Helper.println("Transport area deleted successfully.");
                return true;
            } else {
                Helper.println("Failed to delete transport area.");
            }
        } catch (Exception e) {
            Helper.showError("Error: " + e.getMessage(), e);
        }
        return false;
    }

    /*
     * This method is used to handle the transport area menu.
     * It displays the menu options and prompts the user to select an action.
     * Based on the user's choice, it calls the appropriate method to perform the action.
     */
    public static void HandleTransportAreaMenu() {
        boolean exit = false;
        while (!exit) {
            int choice;
            Helper.println("Transport Area Menu:");
            Helper.println("1. Add Transport Area");
            Helper.println("2. View All Transport Areas");
            Helper.println("3. View Transport Area by ID");
            Helper.println("4. Update Transport Area");
            Helper.println("5. Remove Transport Area");
            Helper.println("6. Return to Main Menu");

            do {
                Helper.println("Please choose an option: ");
                choice = Helper.getUserInputInteger();
            } while (choice == Helper.UndefinedUserInput);

            switch (choice) {
                case 1:
                    addTransportArea();
                    break;
                case 2:
                    printAllTransportAreas();
                    break;
                case 3:
                    printTransportAreaById();
                    break;
                case 4:
                    updateTransportArea();
                    break;
                case 5:
                    // Implement remove transport area logic
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    Helper.showError("Invalid choice. Please try again.");
            }
        }
    }
}
