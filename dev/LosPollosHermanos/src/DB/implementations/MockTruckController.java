package DB.implementations;

import DB.interfaces.ITruckController;
import Models.DBModels.Truck;

import java.util.ArrayList;

public class MockTruckController implements ITruckController {
    private static MockTruckController instance = null;
    private ArrayList<Truck> trucks;
    private int currentId = 1;
    private MockTruckController() {
        // Private constructor to prevent instantiation
        trucks = new ArrayList<>();
    }
    /**
     * Returns the singleton instance of MockTruckController.
     *
     * @return The singleton instance of MockTruckController.
     */
    public static MockTruckController getInstance() {
        if (instance == null) {
            instance = new MockTruckController();
        }
        return instance;
    }
    @Override
    public boolean addTruck(Models.DBModels.Truck truck) {
        if (truck == null) {
            return false;
        }
        truck.setId(currentId++);
        trucks.add(truck);
        return true;
    }
    @Override
    public boolean updateTruck(Models.DBModels.Truck truck, int id) {
        if (truck == null || id <= 0) {
            return false;
        }
        for (int i = 0; i < trucks.size(); i++) {
            if (trucks.get(i).getId() == id) {
                trucks.set(i, truck);
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean deleteTruck(int id) {
        if (id <= 0) {
            return false;
        }
        for (int i = 0; i < trucks.size(); i++) {
            if (trucks.get(i).getId() == id) {
                trucks.remove(i);
                return true;
            }
        }
        return false;
    }
    @Override
    public Models.DBModels.Truck getTruckById(int id) {
        if (id <= 0) {
            return null;
        }
        for (Truck truck : trucks) {
            if (truck.getId() == id) {
                return truck;
            }
        }
        return null;
    }
}
