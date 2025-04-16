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
                        if (choice == 1){
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


}
