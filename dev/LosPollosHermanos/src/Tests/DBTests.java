package Tests;

import DB.DBManager;
import Models.DBModels.suppliers.Supplier;
import Models.DBModels.transport.Branch;
import Models.DBModels.transport.DeliveryFile;
import Models.DBModels.transport.DeliveryFileItem;
import Tests.losPollosAnnotations.UseTestingDatabase;
import Tests.losPollosAnnotations.UseTestingDatabaseExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(UseTestingDatabaseExtension.class)
public class DBTests {

    @UseTestingDatabase
    @Test
    public void testCreateDeliveryFile() {
        DeliveryFileItem item1 = new DeliveryFileItem(0, 101, 5.0, 0.0);
        DeliveryFileItem item2 = new DeliveryFileItem(0, 102, 7.0, 0.0);
        int addedId = DBManager.getInstance().getDeliveryFileController().addDeliveryFile(new DeliveryFile(0,
                List.of(item1, item2)));
        DeliveryFile deliveryFile = DBManager.getInstance().getDeliveryFileController().getDeliveryFileById(addedId);
        assertNotNull(deliveryFile);
        List<DeliveryFileItem> items = deliveryFile.getDeliveryFileItems();
        assertNotNull(items);
        assertNotNull(items.get(0));
        assertNotNull(items.get(1));
        assertEquals(5.0, items.get(0).getAmount());
        assertEquals(7.0, items.get(1).getAmount());
    }

    @UseTestingDatabase
    @Test
    public void testAddItemsToDeliveryFile() {
        DeliveryFileItem item1 = new DeliveryFileItem(0, 101, 5.0, 0.0);
        DeliveryFileItem item2 = new DeliveryFileItem(0, 102, 7.0, 0.0);
        int addedId = DBManager.getInstance().getDeliveryFileController().addDeliveryFile(new DeliveryFile(0,
                List.of(item1, item2)));
        DeliveryFile deliveryFile = DBManager.getInstance().getDeliveryFileController().getDeliveryFileById(addedId);
        assertNotNull(deliveryFile);

        DeliveryFileItem item3 = new DeliveryFileItem(0, 102, 15.2, 0.0);
        boolean result = DBManager.getInstance().getDeliveryFileController().addItemsToDeliveryFile(addedId,
                List.of(item3));
        assertTrue(result);
        deliveryFile = DBManager.getInstance().getDeliveryFileController().getDeliveryFileById(addedId);
        assertNotNull(deliveryFile);
        List<DeliveryFileItem> items = deliveryFile.getDeliveryFileItems();
        assertNotNull(items);
        assertEquals(3, items.size());
        assertEquals(5.0, items.get(0).getAmount());
        assertEquals(7.0, items.get(1).getAmount());
        assertEquals(15.2, items.get(2).getAmount());
    }

    @UseTestingDatabase
    @Test
    public void testRemoveItemsToDeliveryFile() {
        DeliveryFileItem item1 = new DeliveryFileItem(0, 101, 5.0, 0.0);
        DeliveryFileItem item2 = new DeliveryFileItem(0, 102, 7.0, 0.0);
        int addedId = DBManager.getInstance().getDeliveryFileController().addDeliveryFile(new DeliveryFile(0,
                List.of(item1, item2)));
        DeliveryFile deliveryFile = DBManager.getInstance().getDeliveryFileController().getDeliveryFileById(addedId);
        assertNotNull(deliveryFile);
        assertEquals(2, deliveryFile.getDeliveryFileItems().size());

        boolean result = DBManager.getInstance().getDeliveryFileController().removeItemsFromDeliveryFile(addedId,
                List.of(item1));
        assertTrue(result);
        deliveryFile = DBManager.getInstance().getDeliveryFileController().getDeliveryFileById(addedId);
        assertNotNull(deliveryFile);
        List<DeliveryFileItem> items = deliveryFile.getDeliveryFileItems();
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals(7.0, items.get(0).getAmount());
    }

    @UseTestingDatabase
    @Test
    public void testDeleteDeliveryFile() {
        DeliveryFileItem item1 = new DeliveryFileItem(0, 101, 5.0, 0.0);
        DeliveryFileItem item2 = new DeliveryFileItem(0, 102, 7.0, 0.0);
        int addedId = DBManager.getInstance().getDeliveryFileController().addDeliveryFile(new DeliveryFile(0,
                List.of(item1, item2)));
        DeliveryFile deliveryFile = DBManager.getInstance().getDeliveryFileController().getDeliveryFileById(addedId);
        assertNotNull(deliveryFile);
        boolean result = DBManager.getInstance().getDeliveryFileController().deleteDeliveryFile(addedId);
        assertTrue(result);
        deliveryFile = DBManager.getInstance().getDeliveryFileController().getDeliveryFileById(addedId);
        assertNull(deliveryFile);
    }

    @UseTestingDatabase
    @Test
    public void testCreateBranch() {
        Branch branch = DBManager.getInstance().getBranchController().getBranchByName("Branch 1");
        if (branch != null) {
            DBManager.getInstance().getBranchController().deleteBranch(branch.getId());
        }
        branch = new Branch(0, "Branch 1", "123 Main St", "555-1234");
        int addedId = DBManager.getInstance().getBranchController().addBranch(branch);
        Branch retrievedBranch = DBManager.getInstance().getBranchController().getBranchById(addedId);
        assertNotNull(retrievedBranch);

        assertEquals("Branch 1", retrievedBranch.getSiteName());
        assertEquals("123 Main St", retrievedBranch.getSiteAddress());
        assertEquals("555-1234", retrievedBranch.getSitePhone());
    }


    @UseTestingDatabase
    @Test
    public void testUpdateBranch() {
        Branch branch = DBManager.getInstance().getBranchController().getBranchByName("Branch 2");
        if (branch != null) {
            DBManager.getInstance().getBranchController().deleteBranch(branch.getId());
        }
        branch = new Branch(0, "Branch 2", "123 Main Sta", "555-12341");
        int addedId = DBManager.getInstance().getBranchController().addBranch(branch);
        Branch retrievedBranch = DBManager.getInstance().getBranchController().getBranchById(addedId);
        assertNotNull(retrievedBranch);

        retrievedBranch.setSiteName("Updated Branch");
        retrievedBranch.setSitePhone("555-5678111000");
        boolean result = DBManager.getInstance().getBranchController().updateBranch(retrievedBranch, addedId);
        assertTrue(result);
        assertEquals("Updated Branch", retrievedBranch.getSiteName());
        assertEquals("123 Main Sta", retrievedBranch.getSiteAddress());
        assertEquals("555-5678111000", retrievedBranch.getSitePhone());
    }

    @UseTestingDatabase
    @Test
    public void testCreateSupplier() {
        Supplier supplier = DBManager.getInstance().getSupplierController().getSupplierByName("Supplier 1");
        if (supplier != null) {
            DBManager.getInstance().getSupplierController().deleteSupplier(supplier.getId());
        }
        supplier = new Supplier(0, "Supplier 1", "123 Main St test1234", "555-1234666");
        int addedId = DBManager.getInstance().getSupplierController().addSupplier(supplier);
        Supplier retrievedSupplier = DBManager.getInstance().getSupplierController().getSupplierById(addedId);
        assertNotNull(retrievedSupplier);
        assertEquals("Supplier 1", retrievedSupplier.getSiteName());
        assertEquals("123 Main St test1234", retrievedSupplier.getSiteAddress());
        assertEquals("555-1234666", retrievedSupplier.getSitePhone());
    }

    @UseTestingDatabase
    @Test
    public void testUpdateSupplier() {
        Supplier supplier = DBManager.getInstance().getSupplierController().getSupplierByName("Supplier 2");
        if (supplier != null) {
            DBManager.getInstance().getSupplierController().deleteSupplier(supplier.getId());
        }
        supplier = new Supplier(0, "Supplier 2", "123 Main St test1234", "555-1234666");
        int addedId = DBManager.getInstance().getSupplierController().addSupplier(supplier);
        Supplier retrievedSupplier = DBManager.getInstance().getSupplierController().getSupplierById(addedId);
        assertNotNull(retrievedSupplier);

        retrievedSupplier.setSiteName("Updated Supplier");
        retrievedSupplier.setSiteAddress("456 Elm St");
        retrievedSupplier.setSitePhone("555-5678");
        boolean result = DBManager.getInstance().getSupplierController().updateSupplier(retrievedSupplier, addedId);
        assertTrue(result);
        assertEquals("Updated Supplier", retrievedSupplier.getSiteName());
        assertEquals("456 Elm St", retrievedSupplier.getSiteAddress());
        assertEquals("555-5678", retrievedSupplier.getSitePhone());
    }
}
