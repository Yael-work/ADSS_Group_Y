package Presentation.transport;

import Auxiliary.Enums.Domain.UpdateTransportAreaEnum;
import Auxiliary.Helper;
import Models.DBModels.BaseModel;
import Models.DBModels.transport.Branch;
import Models.DBModels.transport.TransportArea;
import Domain.transport.TransportAreasManager;

import java.util.ArrayList;
import java.util.List;

public class TransportAreaHandler {
    private static boolean addTransportArea() {
        String areaName = "";
        ArrayList<Integer> suppliersIds = null; //TODO: Need to be initialized
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
                        } else {
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
                Helper.println(area.toString());
                return true;
            } else {
                Helper.println("No transport area found with the given ID.");
            }
        } catch (Exception e) {
            Helper.showError("Error: " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * This method is used to retrieve a list of IDs from the user.
     * It prompts the user to enter IDs in a specific format and validates them.
     * The valid IDs are added to a list and returned.
     *
     * @param srcLst - the source list of objects to validate the IDs against
     * @return - a list of valid IDs entered by the user
     */
    private static ArrayList<Integer> retrieveIdsListFromInput(List<? extends BaseModel> srcLst) {
        ArrayList<Integer> finalList = new ArrayList<>();
        String idsToAdd;
        String[] idsToAddList;
        do {
            Helper.println("Enter Ids like this: '1,2,3,4,5' or '1' to add a single object.");
            idsToAdd = Helper.getUserInputString();
            if (idsToAdd != null && !idsToAdd.isEmpty()) {
                idsToAddList = idsToAdd.split(",");
                for (String currentId : idsToAddList) {
                    int idToAdd = Integer.parseInt(currentId.trim());
                    if (Helper.isValidId(srcLst, idToAdd)) {
                        finalList.add(idToAdd);
                    } else {
                        Helper.println("Invalid ID: " + idToAdd);
                    }
                }
            } else {
                Helper.println("Invalid input. Please try again.");
            }
        } while (finalList.isEmpty());
        return finalList;
    }

    /**
     * This method is used to update a transport area.
     * It prompts the user to enter the ID of the transport area they want to update,
     * and then allows them to choose which attribute to update.
     * Depending on the user's choice, it updates the transport area accordingly.
     */
    private static void updateTransportArea() {
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
                Helper.println("What would you like to update?");
                for (UpdateTransportAreaEnum e : UpdateTransportAreaEnum.values()) {
                    Helper.println(e.getValue() + ". " + e.getStringLabel());
                }
                Helper.print("Enter your choice: ");
                int choice = Helper.getUserInputInteger();
                UpdateTransportAreaEnum updateChoice = UpdateTransportAreaEnum.intToEnum(choice);
                while (updateChoice == UpdateTransportAreaEnum.UNDEFINED) {
                    Helper.print("Invalid choice. Please enter a valid option: ");
                    choice = Helper.getUserInputInteger();
                    updateChoice = UpdateTransportAreaEnum.intToEnum(choice);
                }

                switch (updateChoice) {
                    case Update_Name -> {
                        Helper.print("Enter new name: ");
                        String newName = Helper.getUserInputString();
                        if (newName != null && !newName.isEmpty()) {
                            if (TransportAreasManager.updateTransportArea(UpdateTransportAreaEnum.Update_Name, id,
                                    newName, null, null)) {
                                Helper.println("Transport area name updated successfully.");
                            } else {
                                Helper.println("Failed to update transport area name.");
                            }
                        } else {
                            Helper.println("Invalid name. Update failed.");
                        }
                    }
                    case Add_Suppliers -> {
                        // TODO Implement adding suppliers
                    }
                    case Remove_Suppliers -> {
                        // TODO Implement removing suppliers
                    }
                    case Add_Branches -> {
                        Helper.println("Choose branches you want to add: ");
                        ArrayList<Branch> branches = BranchesHandler.viewAllBranches();
                        if (branches != null && !branches.isEmpty()) {
                            ArrayList<Integer> lst = retrieveIdsListFromInput(branches);
                            if (!lst.isEmpty()) {
                                TransportAreasManager.updateTransportArea(UpdateTransportAreaEnum.Add_Branches, id,
                                        null, null, lst);
                            }
                        } else {
                            Helper.showError("No branches available to add.");
                        }
                    }
                    case Remove_Branches -> {
                        Helper.println("Choose branches you want to remove: ");
                        ArrayList<Branch> branches = BranchesHandler.viewAllBranches();
                        if (branches != null && !branches.isEmpty()) {
                            ArrayList<Integer> lst = retrieveIdsListFromInput(branches);
                            if (!lst.isEmpty()) {
                                TransportAreasManager.updateTransportArea(UpdateTransportAreaEnum.Remove_Branches, id,
                                        null, null, lst);
                            }
                        } else {
                            Helper.showError("No branches available to remove.");
                        }
                    }
                    default -> {
                        Helper.println("Invalid choice. No updates made.");
                    }
                }
            }
        } catch (Exception e) {
            Helper.showError("Error: " + e.getMessage(), e);
        }
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
            Helper.println("Truck Management Menu:");
            Helper.println("1. Add Transport Area");
            Helper.println("2. Update Transport Area");
            Helper.println("3. Delete Transport Area");
            Helper.println("4. Print Transport area by ID");
            Helper.println("5. Print All Transport Areas");
            Helper.println("6. Back to Main Menu");

            do {
                Helper.println("Please choose an option: ");
                choice = Helper.getUserInputInteger();
            } while (choice == Helper.UndefinedUserInput);

            switch (choice) {
                case 1:
                    addTransportArea();
                    break;
                case 2:
                    updateTransportArea();
                    break;
                case 3:
                    deleteTransportArea();
                    break;
                case 4:
                    printTransportAreaById();
                    break;
                case 5:
                    printAllTransportAreas();
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
