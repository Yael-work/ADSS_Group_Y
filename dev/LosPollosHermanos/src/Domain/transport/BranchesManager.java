package Domain.transport;

import DB.DBManager;
import DB.interfaces.IBranchController;
import Models.DBModels.transport.Branch;
import auxiliary.Helper;

public class BranchesManager {
    private static final IBranchController branchController = DBManager.getInstance().getBranchController();

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
}
