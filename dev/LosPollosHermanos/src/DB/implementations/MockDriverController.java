package DB.implementations;

import DB.interfaces.IDriverController;
import Models.DBModels.Driver;

import java.util.ArrayList;

public class MockDriverController implements IDriverController {
    private static MockDriverController instance = null;
    private ArrayList<Driver> drivers;
    private int currentId = 1;

    private MockDriverController() {
        // Private constructor to prevent instantiation
        drivers = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of MockDriverController.
     *
     * @return The singleton instance of MockDriverController.
     */
    public static MockDriverController getInstance() {
        if (instance == null) {
            instance = new MockDriverController();
        }
        return instance;
    }

    @Override
    public boolean addDriver(Driver driver) {
        if (driver == null) {
            return false;
        }
        driver.setId(currentId++);
        drivers.add(driver);
        return true;
    }

    @Override
    public boolean updateDriver(Driver driver, int id) {
        if (driver == null || id <= 0) {
            return false;
        }
        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getId() == id) {
                drivers.set(i, driver);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteDriver(int id) {
        if (id <= 0) {
            return false;
        }
        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getId() == id) {
                drivers.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public Driver getDriverById(int id) {
        if (id <= 0) {
            return null;
        }
        for (Driver driver : drivers) {
            if (driver.getId() == id) {
                return driver;
            }
        }
        return null;
    }
}
