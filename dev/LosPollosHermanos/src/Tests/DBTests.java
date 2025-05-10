package Tests;

import Auxiliary.Enums.Models.LicenseTypeEnum;
import Auxiliary.Enums.Models.TransportStatusEnum;
import DB.DBManager;
import Models.DBModels.suppliers.Supplier;
import Models.DBModels.transport.*;
import Tests.losPollosAnnotations.UseTestingDatabase;
import Tests.losPollosAnnotations.UseTestingDatabaseExtension;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(UseTestingDatabaseExtension.class)
@TestMethodOrder(MethodOrderer.Random.class)
public class DBTests {

    @UseTestingDatabase
    @Test
    public void testCreateDriver() {
        List<Driver> drivers = DBManager.getInstance().getDriverController().getDriverByFullName("John", "Doe");
        if (drivers != null) {
            for (Driver driver : drivers) {
                DBManager.getInstance().getDriverController().deleteDriver(driver.getId());
            }
        }
        Driver driver = new Driver(0, "John", "Doe", "123456789", "555-1234",
                LicenseTypeEnum.B);
        int addedId = DBManager.getInstance().getDriverController().addDriver(driver);
        Driver retrievedDriver = DBManager.getInstance().getDriverController().getDriverById(addedId);
        assertNotNull(retrievedDriver);
        assertEquals("John", retrievedDriver.getFirstName());
        assertEquals("Doe", retrievedDriver.getLastName());
        assertEquals("555-1234", retrievedDriver.getPhoneNumber());
        assertEquals("123456789", retrievedDriver.getPersonalId());
        assertEquals(LicenseTypeEnum.B, retrievedDriver.getLicenseType());
    }

    @UseTestingDatabase
    @Test
    public void testUpdateDriver() {
        Driver driver = DBManager.getInstance().getDriverController().getDriveByPersonalId("987654321");
        if (driver != null) {
            DBManager.getInstance().getDriverController().deleteDriver(driver.getId());
        }
        driver = DBManager.getInstance().getDriverController().getDriveByPersonalId("123456789");
        if (driver != null) {
            DBManager.getInstance().getDriverController().deleteDriver(driver.getId());
        }
        driver = new Driver(0, "John", "Doe", "123456789", "555-1234",
                LicenseTypeEnum.B);
        int addedId = DBManager.getInstance().getDriverController().addDriver(driver);
        Driver retrievedDriver = DBManager.getInstance().getDriverController().getDriverById(addedId);
        assertNotNull(retrievedDriver);

        retrievedDriver.setFirstName("Updated");
        retrievedDriver.setLastName("Driver");
        retrievedDriver.setPhoneNumber("555-5678");
        retrievedDriver.setPersonalId("987654321");
        boolean result = DBManager.getInstance().getDriverController().updateDriver(retrievedDriver, addedId);
        assertTrue(result);
        assertEquals("Updated", retrievedDriver.getFirstName());
        assertEquals("Driver", retrievedDriver.getLastName());
        assertEquals("987654321", retrievedDriver.getPersonalId());
        assertEquals("555-5678", retrievedDriver.getPhoneNumber());

    }

    @UseTestingDatabase
    @Test
    public void testDeleteDriver() {
        Driver driver = DBManager.getInstance().getDriverController().getDriveByPersonalId("123456789");
        if (driver != null) {
            DBManager.getInstance().getDriverController().deleteDriver(driver.getId());
        }
        driver = new Driver(0, "John", "Doe", "123456789", "555-1234",
                LicenseTypeEnum.B);
        int addedId = DBManager.getInstance().getDriverController().addDriver(driver);
        Driver retrievedDriver = DBManager.getInstance().getDriverController().getDriverById(addedId);
        assertNotNull(retrievedDriver);

        boolean result = DBManager.getInstance().getDriverController().deleteDriver(addedId);
        assertTrue(result);
        retrievedDriver = DBManager.getInstance().getDriverController().getDriverById(addedId);
        assertNull(retrievedDriver);
    }

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
        _testCreateBranch(false);
    }

    private int _testCreateBranch(boolean quitIfExisting) {
        Branch branch = DBManager.getInstance().getBranchController().getBranchByName("Branch 1");
        if (branch != null) {
            if (quitIfExisting) {
                return branch.getId();
            }
            DBManager.getInstance().getBranchController().deleteBranch(branch.getId());
        }
        branch = new Branch(0, "Branch 1", "123 Main St", "555-1234");
        int addedId = DBManager.getInstance().getBranchController().addBranch(branch);
        Branch retrievedBranch = DBManager.getInstance().getBranchController().getBranchById(addedId);
        assertNotNull(retrievedBranch);

        assertEquals("Branch 1", retrievedBranch.getSiteName());
        assertEquals("123 Main St", retrievedBranch.getSiteAddress());
        assertEquals("555-1234", retrievedBranch.getSitePhone());
        return addedId;
    }


    @UseTestingDatabase
    @Test
    public void testUpdateBranch() {
        Branch branch = DBManager.getInstance().getBranchController().getBranchByName("Updated Branch");
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
    public void testDeleteBranch() {
        Branch branch = DBManager.getInstance().getBranchController().getBranchByName("Branch 3");
        if (branch != null) {
            DBManager.getInstance().getBranchController().deleteBranch(branch.getId());
        }
        branch = new Branch(0, "Branch 3", "123 Main St111111", "555-1234111111");
        int addedId = DBManager.getInstance().getBranchController().addBranch(branch);
        Branch retrievedBranch = DBManager.getInstance().getBranchController().getBranchById(addedId);
        assertNotNull(retrievedBranch);

        boolean result = DBManager.getInstance().getBranchController().deleteBranch(addedId);
        assertTrue(result);
        retrievedBranch = DBManager.getInstance().getBranchController().getBranchById(addedId);
        assertNull(retrievedBranch);
    }

    @UseTestingDatabase
    @Test
    public void testCreateSupplier() {
        _testCreateSupplier(false);
    }

    private int _testCreateSupplier(boolean quitIfExisting) {
        Supplier supplier = DBManager.getInstance().getSupplierController().getSupplierByName("Supplier 1");
        if (supplier != null) {
            if (quitIfExisting) {
                return supplier.getId();
            }
            DBManager.getInstance().getSupplierController().deleteSupplier(supplier.getId());
        }
        supplier = new Supplier(0, "Supplier 1", "123 Main St test1234", "555-1234666");
        int addedId = DBManager.getInstance().getSupplierController().addSupplier(supplier);
        Supplier retrievedSupplier = DBManager.getInstance().getSupplierController().getSupplierById(addedId);
        assertNotNull(retrievedSupplier);
        assertEquals("Supplier 1", retrievedSupplier.getSiteName());
        assertEquals("123 Main St test1234", retrievedSupplier.getSiteAddress());
        assertEquals("555-1234666", retrievedSupplier.getSitePhone());
        return addedId;
    }


    @UseTestingDatabase
    @Test
    public void testUpdateSupplier() {
        Supplier supplier = DBManager.getInstance().getSupplierController().getSupplierByName("Updated Supplier");
        if (supplier != null) {
            DBManager.getInstance().getSupplierController().deleteSupplier(supplier.getId());
        }
        supplier = new Supplier(0, "Supplier 2", "11 test addr 123", "05156677888991");
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

    @UseTestingDatabase
    @Test
    public void testDeleteSupplier() {
        Supplier supplier = DBManager.getInstance().getSupplierController().getSupplierByName("Supplier 3");
        if (supplier != null) {
            DBManager.getInstance().getSupplierController().deleteSupplier(supplier.getId());
        }
        supplier = new Supplier(0, "Supplier 3", "delete address 1", "555-054-78974");
        int addedId = DBManager.getInstance().getSupplierController().addSupplier(supplier);
        Supplier retrievedSupplier = DBManager.getInstance().getSupplierController().getSupplierById(addedId);
        assertNotNull(retrievedSupplier);

        boolean result = DBManager.getInstance().getSupplierController().deleteSupplier(addedId);
        assertTrue(result);
        retrievedSupplier = DBManager.getInstance().getSupplierController().getSupplierById(addedId);
        assertNull(retrievedSupplier);
    }

    @UseTestingDatabase
    @Test
    public void testCreateTransportRoute() {
        _testCreateTransportRoute();
    }

    private int _testCreateTransportRoute() {
        //We assume that these function leave a correct creation of branch, supplier, delivery file
        // so that a new delivery file is created, but an ALREADY EXISTING branch and supplier are used
        _testCreateBranch(true);
        _testCreateSupplier(true);
        testCreateDeliveryFile();

        List<Integer> deliveryFileIds = DBManager.getInstance().getDeliveryFileController().getAllIds();
        assertFalse(deliveryFileIds.isEmpty(), "No delivery files found");
        int deliveryFileId = deliveryFileIds.getLast();// we use a one to one, so we take the last one(must be newly created)!

        List<Integer> branchIds = DBManager.getInstance().getBranchController().getAllIds();
        assertFalse(branchIds.isEmpty(), "No branches found");
        int branchId = branchIds.getFirst();

        List<Integer> supplierIds = DBManager.getInstance().getSupplierController().getAllIds();
        assertFalse(supplierIds.isEmpty(), "No suppliers found");
        int supplierId = supplierIds.getFirst();

        DeliveryFile deliveryFile = DBManager.getInstance().getDeliveryFileController().getDeliveryFileById(deliveryFileId);
        assertNotNull(deliveryFile, "Delivery file not found");
        Branch branch = DBManager.getInstance().getBranchController().getBranchById(branchId);
        assertNotNull(branch, "Branch not found");
        Supplier supplier = DBManager.getInstance().getSupplierController().getSupplierById(supplierId);
        assertNotNull(supplier, "Supplier not found");

        // Create a new transport route
        int addedId = DBManager.getInstance().getTransportRouteController().addTransportRoute(
                new TransportRoute(0, TransportStatusEnum.PENDING, deliveryFile, supplier, branch,
                        "John Doe", "555-1234", "Jane Smith",
                        "555-5678", 0.0));
        assertTrue(addedId > 0, "Failed to add transport route");
        TransportRoute transportRoute = DBManager.getInstance().getTransportRouteController().getTransportRouteById(addedId);
        assertNotNull(transportRoute, "Transport route not found");
        assertEquals(TransportStatusEnum.PENDING, transportRoute.getStatus(), "Transport route status mismatch");
        assertEquals(deliveryFileId, transportRoute.getDeliveryFile().getId(), "Delivery file ID mismatch");
        assertEquals(supplierId, transportRoute.getSourceSite().getId(), "Source branch ID mismatch");
        assertEquals(branchId, transportRoute.getDestinationSite().getId(), "Destination supplier ID mismatch");
        assertEquals("John Doe", transportRoute.getSourceContactName(), "Source contact name mismatch");
        assertEquals("555-1234", transportRoute.getSourceContactPhone(), "Source contact phone mismatch");
        assertEquals("Jane Smith", transportRoute.getDestinationContactName(), "Destination contact name mismatch");
        assertEquals("555-5678", transportRoute.getDestinationContactPhone(), "Destination contact phone mismatch");
        assertEquals(0.0, transportRoute.getRecordedWeightAtSite(), "Recorded weight mismatch");
        assertEquals(supplier.getSiteName(), transportRoute.getSourceSite().getSiteName(), "Source site name mismatch");
        assertEquals(branch.getSiteName(), transportRoute.getDestinationSite().getSiteName(), "Destination site name mismatch");
        return addedId;
    }

    @UseTestingDatabase
    @Test
    public void testUpdateTransportRoute() {
        // first we must create a route
        int transportRouteId = _testCreateTransportRoute();

        TransportRoute transportRoute = DBManager.getInstance().getTransportRouteController().getTransportRouteById(transportRouteId);
        assertNotNull(transportRoute, "Transport route not found");
        transportRoute.setStatus(TransportStatusEnum.STARTED);
        transportRoute.setSourceContactName("Updated John Doe");
        transportRoute.setSourceContactPhone("555-9876");
        transportRoute.setDestinationContactName("Updated Jane Smith");
        transportRoute.setDestinationContactPhone("555-6543");
        boolean result = DBManager.getInstance().getTransportRouteController().updateTransportRoute(transportRoute, transportRouteId);
        assertTrue(result, "Failed to update transport route");
        TransportRoute updatedTransportRoute = DBManager.getInstance().getTransportRouteController().getTransportRouteById(transportRouteId);
        assertNotNull(updatedTransportRoute, "Updated transport route not found");
        assertEquals(TransportStatusEnum.STARTED, updatedTransportRoute.getStatus(), "Transport route status mismatch");
        assertEquals("Updated John Doe", updatedTransportRoute.getSourceContactName(), "Source contact name mismatch");
        assertEquals("555-9876", updatedTransportRoute.getSourceContactPhone(), "Source contact phone mismatch");
        assertEquals("Updated Jane Smith", updatedTransportRoute.getDestinationContactName(), "Destination contact name mismatch");
        assertEquals("555-6543", updatedTransportRoute.getDestinationContactPhone(), "Destination contact phone mismatch");
    }

    @UseTestingDatabase
    @Test
    public void testDeleteTransportRoute() {
        // first we must create a route
        int transportRouteId = _testCreateTransportRoute();

        TransportRoute transportRoute = DBManager.getInstance().getTransportRouteController().getTransportRouteById(transportRouteId);
        assertNotNull(transportRoute, "Transport route not found");
        boolean result = DBManager.getInstance().getTransportRouteController().deleteTransportRoute(transportRouteId);
        assertTrue(result, "Failed to delete transport route");
        transportRoute = DBManager.getInstance().getTransportRouteController().getTransportRouteById(transportRouteId);
        assertNull(transportRoute, "Transport route still exists after deletion");
    }

    @UseTestingDatabase
    @Test
    public void testCreateTransportArea() {
        _testCreateTransportArea(false);
    }

    private int _testCreateTransportArea(boolean quitIfExists) {
        TransportArea area = DBManager.getInstance().getTransportAreaController().getTransportAreaByAreaName("Area1");
        if (area != null) {
            if (quitIfExists) {
                return area.getId();
            }
            DBManager.getInstance().getTransportAreaController().deleteTransportArea(area.getId());
        }

        // first we must create a branch and a supplier
        int branchId = _testCreateBranch(true);
        int supplierId = _testCreateSupplier(true);
        Branch existingBranch = DBManager.getInstance().getBranchController().getBranchById(branchId);
        assertNotNull(existingBranch, "Branch not found");
        Supplier existingSupplier = DBManager.getInstance().getSupplierController().getSupplierById(supplierId);
        assertNotNull(existingSupplier, "Supplier not found");

        area = new TransportArea(0, "Area1", List.of(existingSupplier), List.of(existingBranch));
        int createdAreaId = DBManager.getInstance().getTransportAreaController().addTransportArea(area);
        assertTrue(createdAreaId > 0, "Failed to create transport area");
        TransportArea createdArea = DBManager.getInstance().getTransportAreaController().getTransportAreaById(createdAreaId);
        assertNotNull(createdArea, "Created transport area not found");
        assertEquals("Area1", createdArea.getAreaName(), "Transport area name mismatch");
        assertEquals(1, createdArea.getSuppliers().size(), "Transport area suppliers size mismatch");
        assertEquals(1, createdArea.getBranches().size(), "Transport area branches size mismatch");
        assertEquals(existingSupplier.getId(), createdArea.getSuppliers().getFirst().getId(),
                "Transport area supplier ID mismatch");
        assertEquals(existingBranch.getId(), createdArea.getBranches().getFirst().getId(),
                "Transport area branch ID mismatch");
        return createdAreaId;
    }

    @UseTestingDatabase
    @Test
    public void testUpdateTransportArea() {
        TransportArea transportArea = DBManager.getInstance().getTransportAreaController().getTransportAreaByAreaName("Updated Area");
        if (transportArea != null) {
            DBManager.getInstance().getTransportAreaController().deleteTransportArea(transportArea.getId());
        }
        // first we must create a route
        int transportAreaId = _testCreateTransportArea(true);

        transportArea = DBManager.getInstance().getTransportAreaController().getTransportAreaById(transportAreaId);
        assertNotNull(transportArea, "Transport area not found");
        transportArea.setAreaName("Updated Area");
        boolean result = DBManager.getInstance().getTransportAreaController().updateTransportArea(transportArea, transportAreaId);
        assertTrue(result, "Failed to update transport area");
        TransportArea updatedTransportArea = DBManager.getInstance().getTransportAreaController().getTransportAreaById(transportAreaId);
        assertNotNull(updatedTransportArea, "Updated transport area not found");
        assertEquals("Updated Area", updatedTransportArea.getAreaName(), "Transport area name mismatch");
    }

    @UseTestingDatabase
    @Test
    public void testDeleteTransportArea() {
        // first we must create a route
        int transportAreaId = _testCreateTransportArea(true);

        TransportArea transportArea = DBManager.getInstance().getTransportAreaController().getTransportAreaById(transportAreaId);
        assertNotNull(transportArea, "Transport area not found");
        boolean result = DBManager.getInstance().getTransportAreaController().deleteTransportArea(transportAreaId);
        assertTrue(result, "Failed to delete transport area");
        transportArea = DBManager.getInstance().getTransportAreaController().getTransportAreaById(transportAreaId);
        assertNull(transportArea, "Transport area still exists after deletion");
    }

    @UseTestingDatabase
    @Test
    public void testCreateTruck() {
        Truck truck = DBManager.getInstance().getTruckController().getTruckByPlate("ABC123");
        if (truck != null) {
            DBManager.getInstance().getTruckController().deleteTruck(truck.getId());
        }
        truck = new Truck(0, "ABC123", 1000.0, 2000.0, LicenseTypeEnum.C, "Model X");
        int addedId = DBManager.getInstance().getTruckController().addTruck(truck);
        Truck retrievedTruck = DBManager.getInstance().getTruckController().getTruckById(addedId);
        assertNotNull(retrievedTruck);
        assertEquals("ABC123", retrievedTruck.getPlate());
        assertEquals(1000.0, retrievedTruck.getNetWeight());
        assertEquals(2000.0, retrievedTruck.getMaxWeight());
        assertEquals(LicenseTypeEnum.C, retrievedTruck.getRequiredLicenseType());
    }

    @UseTestingDatabase
    @Test
    public void testUpdateTruck() {
        Truck truck = DBManager.getInstance().getTruckController().getTruckByPlate("XYZ789");
        if (truck != null) {
            DBManager.getInstance().getTruckController().deleteTruck(truck.getId());
        }
        truck = DBManager.getInstance().getTruckController().getTruckByPlate("UpdatedXYZ789");
        if (truck != null) {
            DBManager.getInstance().getTruckController().deleteTruck(truck.getId());
        }
        truck = new Truck(0, "XYZ789", 1500.0, 2500.0, LicenseTypeEnum.B, "Model Y");
        int addedId = DBManager.getInstance().getTruckController().addTruck(truck);
        Truck retrievedTruck = DBManager.getInstance().getTruckController().getTruckById(addedId);
        assertNotNull(retrievedTruck);

        retrievedTruck.setPlate("UpdatedXYZ789");
        retrievedTruck.setNetWeight(1600.0);
        retrievedTruck.setMaxWeight(2600.0);
        retrievedTruck.setRequiredLicenseType(LicenseTypeEnum.CPlusE);
        boolean result = DBManager.getInstance().getTruckController().updateTruck(retrievedTruck, addedId);
        assertTrue(result);
        assertEquals("UpdatedXYZ789", retrievedTruck.getPlate());
        assertEquals(1600.0, retrievedTruck.getNetWeight());
        assertEquals(2600.0, retrievedTruck.getMaxWeight());
        assertEquals(LicenseTypeEnum.CPlusE, retrievedTruck.getRequiredLicenseType());
    }

    @UseTestingDatabase
    @Test
    public void testDeleteTruck() {
        Truck truck = DBManager.getInstance().getTruckController().getTruckByPlate("DeleteXYZ789");
        if (truck != null) {
            DBManager.getInstance().getTruckController().deleteTruck(truck.getId());
        }
        truck = new Truck(0, "DeleteXYZ789", 1500.0, 2500.0, LicenseTypeEnum.B, "Model Y");
        int addedId = DBManager.getInstance().getTruckController().addTruck(truck);
        Truck retrievedTruck = DBManager.getInstance().getTruckController().getTruckById(addedId);
        assertNotNull(retrievedTruck);

        boolean result = DBManager.getInstance().getTruckController().deleteTruck(addedId);
        assertTrue(result);
        retrievedTruck = DBManager.getInstance().getTruckController().getTruckById(addedId);
        assertNull(retrievedTruck);
    }
}
