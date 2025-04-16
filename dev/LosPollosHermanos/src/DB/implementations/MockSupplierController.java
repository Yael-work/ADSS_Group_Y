package DB.implementations;

import DB.interfaces.ISupplierController;
import Models.DBModels.suppliers.Supplier;

import java.util.ArrayList;

public class MockSupplierController implements ISupplierController {
    private static MockSupplierController instance = null;
    private final ArrayList<Supplier> suppliers;
    private int idCounter = 1;

    private MockSupplierController() {
        this.suppliers = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of MockSupplierController.
     *
     * @return The singleton instance of MockSupplierController.
     */
    public static MockSupplierController getInstance() {
        if (instance == null) {
            instance = new MockSupplierController();
        }
        return instance;
    }


    @Override
    public boolean addSupplier(Supplier supplier) {
        if (supplier == null) {
            return false;
        }
        supplier.setId(idCounter++);
        suppliers.add(supplier);
        return true;
    }

    @Override
    public boolean updateSupplier(Supplier supplier, int id) {
        if (supplier == null || id <= 0) {
            return false;
        }
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getId() == id) {
                suppliers.set(i, supplier);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteSupplier(int id) {
        if (id <= 0) {
            return false;
        }
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getId() == id) {
                suppliers.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public Supplier getSupplierById(int id) {
        if (id <= 0) {
            return null;
        }
        for (Supplier supplier : suppliers) {
            if (supplier.getId() == id) {
                return supplier;
            }
        }
        return null;
    }

    @Override
    public Supplier getSupplierByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        for (Supplier supplier : suppliers) {
            if (supplier.getSiteName().equalsIgnoreCase(name)) {
                return supplier;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Supplier> getSuppliersByIds(ArrayList<Integer> ids) {
        ArrayList<Supplier> result = new ArrayList<>();
        if (ids == null || ids.isEmpty()) {
            return result;
        }
        for (Integer id : ids) {
            Supplier supplier = getSupplierById(id);
            if (supplier != null) {
                result.add(supplier);
            }
        }
        return result;
    }

    @Override
    public final ArrayList<Supplier> getAllRecords() {
        return suppliers;
    }
}
