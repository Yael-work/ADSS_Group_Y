package Domain.suppliers;

import Auxiliary.Helper;
import DB.DBManager;
import DB.interfaces.ISupplierController;
import Models.DBModels.suppliers.Supplier;

import java.util.ArrayList;

/**
 * This is a temp mock class for the suppliers manager.
 * TODO - @mohamed  should complete the implementation of this class
 */
public class SuppliersManager {
    private static final ISupplierController supplierController = DBManager.getInstance()
            .getSupplierController();

    public static ArrayList<Supplier> getSuppliers() {
        return supplierController.getAllRecords();
    }

    public static Supplier getSupplierById(int id) {
        return supplierController.getSupplierById(id);
    }

    public static Supplier getSupplierByName(String name) {
        return supplierController.getSupplierByName(name);
    }

    public static void addSupplier(String name, String address, String phone) {
        Supplier s = getSupplierByName(name);
        if (s != null) {
            Helper.showError("Supplier with this name already exists");
        }
        s = new Supplier(Helper.UndefinedUserInput, name, address, phone);
        if (!supplierController.addSupplier(s)) {
            Helper.showError("Failed to add supplier");
        }
    }
}
