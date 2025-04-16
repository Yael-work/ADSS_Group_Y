package Domain.transport;

import DB.DBManager;
import DB.interfaces.IBranchController;
import Models.DBModels.transport.Branch;
import Auxiliary.Helper;

import java.util.ArrayList;

public class BranchesManager {
    private static final IBranchController branchController = DBManager.getInstance().getBranchController();

    /**
     * Checks if a branch with the given name, address, or phone number already exists in the system.
     *
     * @param name       - the name of the branch
     * @param address    - the address of the branch
     * @param phone      - the phone number of the branch
     * @param providedId - the ID of the branch to be ignored during the check
     * @return - the ID of the existing branch if found, or Helper.UndefinedId if not found
     */
    private static int doesBranchExist(String name, String address, String phone, int providedId) {
        Branch b = branchController.getBranchByAddress(address);
        if (b != null && (providedId == Helper.UndefinedId || b.getId() != providedId)) {
            Helper.showError("Branch with this address already exists");
            return b.getId();
        }
        b = branchController.getBranchByPhoneNumber(phone);
        if (b != null && (providedId == Helper.UndefinedId || b.getId() != providedId)) {
            Helper.showError("Branch with this phone number already exists");
            return b.getId();
        }
        b = branchController.getBranchByName(name);
        if (b != null && (providedId == Helper.UndefinedId || b.getId() != providedId)) {
            Helper.showError("Branch with this name already exists");
            return b.getId();
        }
        return Helper.UndefinedId;
    }

    /**
     * Checks if a branch with the given name, address, or phone number already exists in the system.
     *
     * @param name    - the name of the branch
     * @param address - the address of the branch
     * @param phone   - the phone number of the branch
     * @return - the ID of the existing branch if found, or Helper.UndefinedId if not found
     */
    private static int doesBranchExist(String name, String address, String phone) {
        return doesBranchExist(name, address, phone, Helper.UndefinedId);
    }

    /**
     * Adds a new branch to the system.
     *
     * @param name    - the name of the branch
     * @param address - the address of the branch
     * @param phone   - the phone number of the branch
     * @return - true if the branch was added successfully, false otherwise
     */
    public static boolean addBranch(String name, String address, String phone) {
        if (doesBranchExist(name, address, phone) != Helper.UndefinedId) {
            // Branch with this name, address, or phone number already exists
            return false;
        }
        Branch b = new Branch(0, name, address, phone);
        return branchController.addBranch(b);
    }

    /**
     * Retrieves the entire list of branches from the database.
     *
     * @return - an ArrayList of Branch objects representing all branches in the system
     */
    public static ArrayList<Branch> getExistingBranches() {
        return branchController.getAllRecords();
    }

    /**
     * Retrieves a branch by its ID.
     *
     * @param id - the ID of the branch to be retrieved
     * @return - the Branch object with the specified ID, or null if not found
     */
    public static Branch getBranchById(int id) {
        return branchController.getBranchById(id);
    }

    /**
     * Updates the details of an existing branch.
     *
     * @param id      - the ID of the branch to be updated
     * @param name    - the new name of the branch
     * @param address - the new address of the branch
     * @param phone   - the new phone number of the branch
     * @return - true if the branch was updated successfully, false otherwise
     */
    public static boolean updateBranch(int id, String name, String address, String phone) {
        Branch b = branchController.getBranchById(id);
        if (b == null) {
            Helper.showError("Branch with this ID does not exist");
            return false;
        }
        if (doesBranchExist(name, address, phone, id) != Helper.UndefinedId) {
            // Branch with this name, address, or phone number already exists
            return false;
        }
        b.setSiteName(name);
        b.setAddress(address);
        b.setPhone(phone);
        return branchController.updateBranch(b);
    }

    /**
     * Deletes a branch from the system.
     *
     * @param id - the ID of the branch to be deleted
     * @return - true if the branch was deleted successfully, false otherwise
     */
    public static boolean deleteBranch(int id) {
        Branch b = branchController.getBranchById(id);
        if (b == null) {
            Helper.showError("Branch with this ID does not exist");
            return false;
        }
        return branchController.deleteBranch(id);
    }
}
