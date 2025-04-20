package Tests;

import Domain.transport.TrucksManager;
import Models.DBModels.transport.Truck;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TrucksManagerNoMockTest {

    private TrucksManager stub;

    @Test
    public void testAddTruck_Success() {
        boolean result = TrucksManager.addTruck("ABC123", 1000, 5000, "C", "Volvo");
        assertTrue(result);
        Truck truck = TrucksManager.getTruckById(1);
        assertNotNull(truck);
    }

    @Test
    public void testUpdateTruck_ChangePlate() {
        TrucksManager.addTruck("DEF456", 1500, 6000, "C1", "MAN");
        Truck truck = TrucksManager.getTruckById(2);
        boolean result = TrucksManager.updateTruck("NEWPLATE", truck.getId(), 1);
        assertTrue(result);
        assertEquals("NEWPLATE", stub.getTruckById(truck.getId()).getPlate());
    }

    @Test
    public void testDeleteTruck_Success() {
        TrucksManager.addTruck("XYZ789", 2000, 7000, "C", "Scania");
        Truck truck = TrucksManager.getTruckById(3);
        assert truck != null;
        boolean result = TrucksManager.deleteTruck(truck.getId());
        assertTrue(result);
        assertNull(TrucksManager.getTruckById(truck.getId()));
    }
}