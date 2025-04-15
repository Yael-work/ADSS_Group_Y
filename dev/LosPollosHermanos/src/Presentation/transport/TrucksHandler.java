package Presentation.transport;

import Auxiliary.Helper;

public class TrucksHandler {
    public boolean addTruck() {
        String plate;
        double netWeight;
        double maxWeight;
        String requiredLicenseType;
        String model;
        try {
            System.out.println("Truck creation:");
            do {
                System.out.print("Enter Truck plate: ");
                plate = Helper.getUserInputString();
            } while (plate == null || plate.isEmpty());
            do {
                System.out.print("Enter Truck net weight: ");
                netWeight = Helper.getUserInputDouble();
            } while (netWeight <= 0);
            do {
                System.out.print("Enter Truck max weight: ");
                maxWeight = Helper.getUserInputDouble();
            } while (maxWeight <= 0);
            do {
                System.out.print("Enter Truck required license type: ");
                requiredLicenseType = Helper.getUserInputString();
            } while (requiredLicenseType == null || requiredLicenseType.isEmpty());
            do {
                System.out.print("Enter Truck model: ");
                model = Helper.getUserInputString();
            } while (model == null || model.isEmpty());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }
}
