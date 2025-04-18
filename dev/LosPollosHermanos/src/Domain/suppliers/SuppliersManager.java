package Domain.suppliers;

import Auxiliary.Helper;
import DB.DBManager;
import DB.interfaces.ISupplierController;
import Models.DBModels.suppliers.Supplier;
import Models.DTOs.ItemsAtSupplier;
import Models.DTOs.StockItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


    /**
     * This method is used to get the items that can be provided from the supplier in accordance with the requested items and their amounts
     *
     * @param supplierId - the ID of the supplier
     * @param items      - the list of items to be provided
     * @return - a list of items at the supplier
     */
    public static List<ItemsAtSupplier> mockCanSupplierProvideRequestedItems(int supplierId, List<StockItem> items) {
        List<ItemsAtSupplier> itemsAtSupplier = new ArrayList<>();
        Random random = new Random();
        for (StockItem item : items) {
            double originalAmount = item.getAmount();
            double randomAmount = originalAmount - 5 + (10 * random.nextDouble()); // Generate random value in range [amount - 5, amount + 5]
            randomAmount = Math.max(0, randomAmount);

            if (randomAmount >= originalAmount) {
                ItemsAtSupplier itemsAtSupplier1 = new ItemsAtSupplier(supplierId, item.getItemId(), randomAmount);
                itemsAtSupplier.add(itemsAtSupplier1);
            }
        }
        return itemsAtSupplier;
    }

    /**
     * This method is used to reserve the items of the supplier
     *
     * @param supplierId - the ID of the supplier
     * @param items      - the list of items to be reserved
     * @return - true if the items were reserved successfully, false otherwise
     */
    public static boolean mockReserveItemsOfSupplier(int supplierId, List<StockItem> items) {
        //todo @ whoever
        return true;
    }


    /**
     * This method is used to un-reserve the items of the supplier
     *
     * @param supplierId - the ID of the supplier
     * @param items      - the list of items to be un-reserved
     * @return - true if the items were un-reserved successfully, false otherwise
     */
    public static boolean mockUnReserveItemsOfSupplier(int supplierId, List<StockItem> items) {
        //todo @ whoever
        return true;
    }
}
