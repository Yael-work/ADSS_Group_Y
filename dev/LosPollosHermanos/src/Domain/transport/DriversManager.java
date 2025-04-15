package Domain.transport;

import DB.DBManager;
import DB.interfaces.IDriverController;
import Models.DBModels.transport.Driver;
import Auxiliary.Helper;

public class DriversManager {
    private static final IDriverController driverController = DBManager.getInstance().getDriverController();

    public static boolean addDriver(String firstName, String lastName, String personalId, String phoneNumber, String licenseType) {
        Driver d = driverController.getDriveByPersonalId(personalId);
        if (d != null) {
            Helper.showError("Driver with this personal ID already exists", null);
            return false;
        }
        d = driverController.getDriverByPhoneNumber(phoneNumber);
        if (d != null) {
            Helper.showError("Driver with this phone number already exists", null);
            return false;
        }
        d = driverController.getDriverByFullName(firstName, lastName);
        if (d != null) {
            Helper.showError("Driver with this name already exists", null);
            return false;
        }
        d = new Driver(0, firstName, lastName, personalId, phoneNumber, licenseType);
        return driverController.addDriver(d);
    }

    public static boolean updateDriver(String some , int id, int value) {
        Driver d = driverController.getDriverById(id);
        if (d == null) {
            Helper.showError("Driver with this ID does not exist", null);
            return false;
        }
        switch (value) {
            case 1:
                d.setFirstName(Helper.getUserInputString());
                break;
            case 2:
                d.setLastName(Helper.getUserInputString());
                break;
            case 3:
                d.setPersonalId(Helper.getUserInputString());
                break;
            case 4:
                d.setPhoneNumber(Helper.getUserInputString());
                break;
            case 5:
                d.setLicenseType(Helper.getUserInputString());
                break;
            default:
                Helper.showError("Invalid choice", null);
                return false;
        }
        return driverController.updateDriver(d, id);
    }

    public static boolean deleteDriver(int id) {
        Driver d = driverController.getDriverById(id);
        if (d == null) {
            Helper.showError("Driver with this ID does not exist", null);
            return false;
        }
        return driverController.deleteDriver(id);
    }
}
