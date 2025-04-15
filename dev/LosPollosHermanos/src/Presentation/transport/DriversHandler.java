package Presentation.transport;

import auxiliary.Helper;

public class DriversHandler {
    public boolean addDriver() {
         String firstName = null;
         String lastName = null;
         String personalId = null;
         String phoneNumber = null;
         String licenseType = null;

        try {
            System.out.println("Driver creation:");
            do {
                System.out.print("Enter Driver first name: ");
                firstName = Helper.getUserInputString();
            } while (firstName == null || firstName.isEmpty());
            do {
                System.out.print("Enter Driver last name: ");
                lastName = Helper.getUserInputString();
            } while (lastName == null || lastName.isEmpty());
            do {
                System.out.print("Enter Driver personal ID: ");
                personalId = Helper.getUserInputString();
            } while (personalId == null || personalId.isEmpty());
            do {
                System.out.print("Enter Driver phone number: ");
                phoneNumber = Helper.getUserInputString();
            } while (phoneNumber == null || phoneNumber.isEmpty());
            do {
                System.out.print("Enter Driver license type: ");
                licenseType = Helper.getUserInputString();
            } while (licenseType == null || licenseType.isEmpty());
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }
}