package Tests.transport;

import Domain.transport.TransportAreasManager;
import Domain.transport.TrucksManager;
import Domain.transport.BranchesManager;
import Domain.transport.DriversManager;
import Models.DBModels.transport.Branch;
import Models.DBModels.transport.Driver;
import Models.DBModels.transport.TransportArea;
import Models.DBModels.transport.Truck;
import Tests.losPollosAnnotations.UseTestingDatabase;
import Tests.losPollosAnnotations.UseTestingDatabaseExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(UseTestingDatabaseExtension.class)
public class DomainTests {

    @UseTestingDatabase
    @Test
    public void testAddTruck_Success() {
        boolean result = TrucksManager.addTruck("ABC123", 1000, 5000, "C", "Volvo");
        assertTrue(result);
        Truck truck = TrucksManager.getTruckByPlate("ABC123");
        assertNotNull(truck);
        assertEquals("ABC123", truck.getPlate());
    }

    @UseTestingDatabase
    @Test
    public void testUpdateTruck_ChangePlate() {
        TrucksManager.addTruck("DEF456", 1500, 6000, "C1", "MAN");
        Truck truck = TrucksManager.getTruckByPlate("DEF456");
        assert truck != null;
        boolean result = TrucksManager.updateTruck("NEWPLATE", truck.getId(), 1);
        assertTrue(result);
        Truck updatedTruck = TrucksManager.getTruckById(truck.getId());
        assert updatedTruck != null;
        assertEquals("NEWPLATE", updatedTruck.getPlate());
    }

    @UseTestingDatabase
    @Test
    public void testDeleteTruck_Success() {
        TrucksManager.addTruck("XYZ789", 2000, 7000, "C", "Scania");
        Truck truck = TrucksManager.getTruckByPlate("XYZ789");
        assert truck != null;
        boolean result = TrucksManager.deleteTruck(truck.getId());
        assertTrue(result);
        assertNull(TrucksManager.getTruckById(truck.getId()));
    }

    @UseTestingDatabase
    @Test
    public void testAddBranch_Success() {
        Branch b = BranchesManager.getBranchByName("Main Office");
        if (b != null) {
            BranchesManager.deleteBranch(b.getId());
        }
        boolean result = BranchesManager.addBranch("Main Office", "123 Street", "0500000000");
        assertTrue(result);
        b = BranchesManager.getBranchByName("Main Office");
        assertNotNull(b);
        assertEquals("Main Office", b.getSiteName());
    }

    @UseTestingDatabase
    @Test
    public void testUpdateBranch_Success() {
        BranchesManager.addBranch("Warehouse", "456 Avenue", "0511111111");
        Branch b = BranchesManager.getBranchByName("Warehouse");
        assertNotNull(b);

        String newName = new Random().nextInt(1000) + "UpdatedBranchName";
        String newAddress = new Random().nextInt(1000) + "UpdatedBranchAddress";
        String newPhone = new Random().nextInt(1000) + "UpdatedBranchPhone";
        boolean result = BranchesManager.updateBranch(b.getId(), newName, newAddress, newPhone);
        assertTrue(result);
    }

    @UseTestingDatabase
    @Test
    public void testDeleteBranch_Success() {
        BranchesManager.addBranch("Temp Office", "999 Lane", "0533333333");
        Branch b = BranchesManager.getBranchByName("Temp Office");
        assert b != null;
        boolean result = BranchesManager.deleteBranch(b.getId());
        assertTrue(result);
        assertNull(BranchesManager.getBranchById(b.getId()));
    }

    @UseTestingDatabase
    @Test
    public void testAddDriver_Success() {
        Driver d = DriversManager.getDriverByPersonalId("123456789");
        if (d != null) {
            DriversManager.deleteDriver(d.getId());
        }
        boolean result = DriversManager.addDriver("John", "Doe", "123456789", "0500000000", "B");
        assertTrue(result);
        d = DriversManager.getDriverByPersonalId("123456789");
        assertNotNull(d);
    }

    @UseTestingDatabase
    @Test
    public void testUpdateDriver_Success() {
        DriversManager.addDriver("Alice", "Smith", "987654321", "0501111111", "C");
        Driver driver = DriversManager.getDriverByPersonalId("987654321");
        assert driver != null;
        boolean result = DriversManager.updateDriver("AliceUpdated", driver.getId(), 1);
        assertTrue(result);
        driver = DriversManager.getDriverByPersonalId("987654321");
        assertNotNull(driver);
        assertEquals("AliceUpdated", driver.getFirstName());
    }

    @UseTestingDatabase
    @Test
    public void testDeleteDriver_Success() {
        DriversManager.addDriver("Mark", "Brown", "321654987", "0502222222", "C1");
        Driver driver1 = DriversManager.getAllDrivers().stream()
                .filter(driver2 -> driver2.getFirstName().equals("Mark"))
                .findFirst()
                .orElse(null);
        assert driver1 != null;
        boolean result = DriversManager.deleteDriver(driver1.getId());
        assertTrue(result);
        assertNull(DriversManager.getDriverById(driver1.getId()));
    }

    @UseTestingDatabase
    @Test
    public void testAddNewTransportArea_Success() {
        String areaName = "North Region";
        List<Integer> branches = List.of(1, 2);
        List<Integer> suppliers = List.of(10, 20);
        boolean result = TransportAreasManager.addNewTransportArea(areaName, branches, suppliers);
        assertTrue(result);
        assertEquals(1, TransportAreasManager.getAllTransportAreas().size());
        TransportArea area = TransportAreasManager.getAllTransportAreas().stream()
                .filter(area1 -> area1.getAreaName().equals(areaName))
                .findFirst()
                .orElse(null);
        assert area != null;
        assertEquals(areaName, area.getAreaName());
        assertEquals(branches, area.getBranches());
        assertEquals(suppliers, area.getSuppliers());
    }
}