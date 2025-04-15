package Domain.transport;

import Models.DBModels.transport.Truck;
import DB.DBManager;
import DB.interfaces.ITruckController;
import Auxiliary.Helper;

import java.util.ArrayList;

public class TrucksManager {
    private static final ITruckController truckController = DBManager.getInstance().getTruckController();

    public static boolean addTruck(String plate, double netWeight, double maxWeight, String requiredLicenseType, String model) {
        Truck t = truckController.getTruckByPlate(plate);
        if (t != null) {
            Helper.showError("Truck with this plate already exists", null);
            return false;
        }
        t = new Truck(0, plate, netWeight, maxWeight, requiredLicenseType, model);
        return truckController.addTruck(t);
    }

    public static boolean updateTruck(String some , int id, int value) {
        Truck t = truckController.getTruckById(id);
        if (t == null) {
            Helper.showError("Truck with this ID does not exist", null);
            return false;
        }
        switch (value) {
            case 1:
                t.setPlate(Helper.getUserInputString());
                break;
            case 2:
                t.setNetWeight(Helper.getUserInputDouble());
                break;
            case 3:
                t.setMaxWeight(Helper.getUserInputDouble());
                break;
            case 4:
                t.setRequiredLicenseType(Helper.getUserInputString());
                break;
            case 5:
                t.setModel(Helper.getUserInputString());
                break;
            default:
                Helper.showError("Invalid choice", null);
                return false;
        }
        return truckController.updateTruck(t, id);
    }

    public static boolean deleteTruck(int id) {
        Truck t = truckController.getTruckById(id);
        if (t == null) {
            Helper.showError("Truck with this ID does not exist", null);
            return false;
        }
        return truckController.deleteTruck(id);
    }

    public static ArrayList<Truck> getAllTrucks() {
        return truckController.getAllRecords();
    }

    public static Truck getTruckById(int id) {
        Truck t = truckController.getTruckById(id);
        if (t == null) {
            Helper.showError("Truck with this ID does not exist", null);
            return null;
        }
        return t;
    }
}
