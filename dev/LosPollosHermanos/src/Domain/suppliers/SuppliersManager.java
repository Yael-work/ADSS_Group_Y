package Domain.suppliers;

import Auxiliary.Helper;
import Models.DBModels.suppliers.Supplier;

import java.util.ArrayList;

/**
 * This is a temp mock class for the suppliers manager.
 * TODO - @mohamed  should complete the implementation of this class
 */
public class SuppliersManager {
    private static final ArrayList<Supplier> lst = new ArrayList<>();
    private static int idCounter = 1;

    public static ArrayList<Supplier> getSuppliers() {
        return lst;
    }

    public static Supplier getSupplierById(int id) {
        for (Supplier s : lst) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public static Supplier getSupplierByName(String name) {
        for (Supplier s : lst) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    public static boolean addSupplier(String name, String address, String phone) {
        Supplier s = getSupplierByName(name);
        if (s != null) {
            Helper.showError("Supplier with this name already exists");
            return false;
        }
        s = new Supplier(idCounter++, name, address, phone);
        lst.add(s);
        return true;
    }
}
