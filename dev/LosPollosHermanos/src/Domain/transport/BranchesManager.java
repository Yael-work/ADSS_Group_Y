package Domain.transport;

import DB.DBManager;
import DB.interfaces.IBranchController;
import Models.DBModels.transport.Branch;
import auxiliary.Helper;

import java.util.ArrayList;

public class BranchesManager {
    private static final IBranchController branchController = DBManager.getInstance().getBranchController();

    /**
     * Adds a new branch to the system.
     *
     * @param name    - the name of the branch
     * @param address - the address of the branch
     * @param phone   - the phone number of the branch
     * @return - true if the branch was added successfully, false otherwise
     */
    public static boolean addBranch(String name, String address, String phone) {
        Branch b = branchController.getBranchByAddress(address);
        if (b != null) {
            Helper.showError("Branch with this address already exists", null);
            return false;
        }
        b = branchController.getBranchByPhoneNumber(phone);
        if (b != null) {
            Helper.showError("Branch with this phone number already exists", null);
            return false;
        }
        b = branchController.getBranchByName(name);
        if (b != null) {
            Helper.showError("Branch with this name already exists", null);
            return false;
        }
        b = new Branch(0, name, address, phone);
        return branchController.addBranch(b);
    }

    /**
     * Retrieves the entire list of branches from the database.
     *
     * @return - an ArrayList of Branch objects representing all branches in the system
     */
    public static ArrayList<Branch> viewExistingBranchesIDs() {
        return branchController.getAllRecords();
    }

    /**
     * Retrieves a branch by its ID.
     * @param id - the ID of the branch to be retrieved
     * @return - the Branch object with the specified ID, or null if not found
     */
    public static Branch getBranchById(int id) {
        return branchController.getBranchById(id);
    }
}
