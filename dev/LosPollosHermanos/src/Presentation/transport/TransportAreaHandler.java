package Presentation.transport;

import Auxiliary.Helper;
import Models.DBModels.transport.Branch;
import Models.DBModels.transport.TransportArea;
import DB.interfaces.ITransportAreaController;
import Domain.transport.TransportAreasManager;
import Presentation.transport.BranchesHandler;

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

    /**
     * Check if the given ID is valid by checking if it exists in the list of transport areas.
     *
     * @param areas   - the list of transport areas
     * @param checkId - the ID to check
     * @return true if the ID is valid, false otherwise
     */
    private static boolean isValidId(ArrayList<TransportArea> areas, int checkId) {
        return checkId > 0 && areas.stream().anyMatch(a -> a.getId() == checkId);
    }


    private static boolean updateTransportArea() {
        try {
            Helper.println("Transport Area update:");
            Helper.println("Which transport area do you want to update?");
            ArrayList<TransportArea> areas = printAllTransportAreas();
            if (areas != null && !areas.isEmpty()) {
                int id = Helper.getUserInputInteger();
                while (!isValidId(areas, id)) {
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
