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
            String name;
            do {
                Helper.println("Enter Transport Area name: ");
                name = Helper.getUserInputString();
            } while (name == null || name.isEmpty());
            ArrayList<Branch> b = BranchesHandler.viewAllBranches();
            if (b == null || b.isEmpty()) {
                Helper.showError("No branches available. Cannot create a transport area.");
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
                            f = true;
                            break;
                        }
                    }
                    if (!f) {
                        Helper.println("Invalid id. try again.");
                    } else {
                        int choice = 0;
                        do {
                            Helper.println("You want to add more? press 1 for yes or 2 for no.");
                            choice = Helper.getUserInputInteger();
                            if (choice == 1 || choice == 2) {
                                break;
                            } else {
                                Helper.println("Error, please try again. ");
                            }
                        } while (true);
                        if (choice == 1) {
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

}
