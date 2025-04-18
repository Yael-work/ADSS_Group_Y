package Domain.suppliers;

import Auxiliary.Helper;
import DB.DBManager;
import DB.interfaces.ISupplierController;
import Models.DBModels.suppliers.Supplier;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Supplier> getSuppliersByIds(List<Integer> ids) {
        return supplierController.getSuppliersByIds(ids);
    }

    public static Supplier getSupplierByName(String name) {
        return supplierController.getSupplierByName(name);
    }

    public static boolean addSupplier(String name, String address, String phone) {
        Supplier s = getSupplierByName(name);
        if (s != null) {
            Helper.showError("Supplier with this name already exists");
        }
        s = new Supplier(Helper.UndefinedUserInput, name, address, phone);
        return supplierController.addSupplier(s);
    }
}
