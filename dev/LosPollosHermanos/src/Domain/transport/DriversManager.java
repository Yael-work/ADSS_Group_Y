package Domain.transport;

import Auxiliary.Enums.Models.LicenseTypeEnum;
import DB.DBManager;
import DB.interfaces.transport.IDriverController;
import Models.DBModels.transport.Driver;
import Auxiliary.Helper;

import java.util.ArrayList;

/**
 * The DriversManager class is responsible for managing driver-related operations.
 * It provides methods to add, update, delete, and retrieve drivers from the database.
 */
public class DriversManager {
    private static final IDriverController driverController = DBManager.getInstance().getDriverController();

    /**
     * Adds a new driver to the system.
     *
     * @param firstName   - the first name of the driver
     * @param lastName    - the last name of the driver
     * @param personalId  - the personal ID of the driver
     * @param phoneNumber - the phone number of the driver
     * @param licenseType - the license type of the driver
     * @return true if the driver was added successfully, false otherwise
     */
    public static boolean addDriver(String firstName, String lastName, String personalId, String phoneNumber, String licenseType) {
        Driver d = driverController.getDriveByPersonalId(personalId);
        if (d != null) {
            Helper.showError("Driver with this personal ID already exists");
            return false;
        }
        d = driverController.getDriverByPhoneNumber(phoneNumber);
        if (d != null) {
            Helper.showError("Driver with this phone number already exists");
            return false;
        }
        /*It is possible for multiple people to have the same name - it is not unique - commenting our for now...*/
//        d = driverController.getDriverByFullName(firstName, lastName);
//        if (d != null) {
//            Helper.showError("Driver with this name already exists");
//            return false;
//        }
        d = new Driver(0, firstName, lastName, personalId, phoneNumber, LicenseTypeEnum.stringToEnum(licenseType));
        if (driverController.addDriver(d) != Helper.UndefinedId) {
            //todo @ethan link with employees module
            return true;
        }
        return false;
    }

    /**
     * Updates a driver's information based on user input.
     *
     * @param some  - a string that is not used in the method
     * @param id    - the ID of the driver to be updated
     * @param value - the field to be updated (1: first name, 2: last name, 3: personal ID, 4: phone number, 5: license type)
     * @return true if the update was successful, false otherwise
     */
    public static boolean updateDriver(String some, int id, int value) {
        Driver d = driverController.getDriverById(id);
        if (d == null) {
            Helper.showError("Driver with this ID does not exist");
            return false;
        }
        switch (value) {
            case 1:
                d.setFirstName(some);
                break;
            case 2:
                d.setLastName(some);
                break;
            case 3:
                d.setPersonalId(some);
                break;
            case 4:
                d.setPhoneNumber(some);
                break;
            case 5:
                d.setLicenseType(LicenseTypeEnum.stringToEnum(some));
                break;
            default:
                Helper.showError("Invalid choice");
                return false;
        }
        return driverController.updateDriver(d, id);
    }

    /**
     * Deletes a driver from the system.
     *
     * @param id - the ID of the driver to be deleted
     * @return true if the driver was deleted successfully, false otherwise
     */
    public static boolean deleteDriver(int id) {
        Driver d = driverController.getDriverById(id);
        if (d == null) {
            Helper.showError("Driver with this ID does not exist");
            return false;
        }
        return driverController.deleteDriver(id);
    }

    /**
     * Retrieves a driver by their ID.
     *
     * @param id - the ID of the driver to be retrieved
     * @return - the Driver object with the specified ID, or null if not found
     */
    public static Driver getDriverById(int id) {
        Driver d = driverController.getDriverById(id);
        if (d == null) {
            Helper.showError("Driver with this ID does not exist");
            return null;
        }
        return d;
    }

    /**
     * Retrieves all drivers from the system.
     *
     * @return - an ArrayList of Driver objects representing all drivers in the system
     */
    public static ArrayList<Driver> getAllDrivers() {
        ArrayList<Driver> drivers = new ArrayList<>();
        for (Integer id : driverController.getAllIds()) {
            Driver driver = driverController.getDriverById(id);
            if (driver == null) {
                Helper.showError(String.format("Cant get driver with id=%d, it does not exist", id));
            } else {
                drivers.add(driver);
            }
        }
        return drivers;
    }

    /**
     * Retrieves a driver by their personal ID.
     *
     * @param personalId - the personal ID of the driver to be retrieved
     * @return - the Driver object with the specified personal ID, or null if not found
     */
    public static Driver getDriverByPersonalId(String personalId) {
        Driver d = driverController.getDriveByPersonalId(personalId);
        if (d == null) {
            Helper.showError("Driver with this personal ID does not exist");
            return null;
        }
        return d;
    }
}
