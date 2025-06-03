package Presentation.Suppliers;

import Auxiliary.SupplierHelper;
import DTO.Suppliers.SupplierDTO;
import Models.DBModels.suppliers.Supplier;
import Services.SuppliersServices;

import java.util.Scanner;

public class SupplierDeletionUI {
    // DBManager to interact with the database

    // Scanner for user input
    Scanner in;

    /**
     * Constructor for SupplierDeletionUI.
     * Sets up the DBManager and Scanner instances.
     */
    public SupplierDeletionUI() {
        in = new Scanner(System.in);
    }

    /**
     * Starts the supplier deletion process.
     * Asks the user for supplier number and deletes it if found.
     */
    public void start() {
        System.out.println("--------------Supplier Deletion Form----------------");

        // Check if there are any suppliers at all
        if (SuppliersServices.getNumberOfSuppliersInDatabase() == 0) {
            System.out.println("no suppliers are in the system");
            return;
        }

        String supNumber = SupplierHelper.getNonEmptyStringFromUser("Enter the Number of the Supplier you want to delete : ") ;
        SupplierDTO supplier = SuppliersServices.getSupplierByNumber(supNumber);

        // If supplier not found, tell the user
        if (supplier == null) {
            System.out.println("Supplier Not Found");
            return;
        }

        // Delete the supplier
        if(SuppliersServices.deleteSupplier(supNumber))
            System.out.println("Supplier Deleted successfully");
        else
            System.out.println("An error occurred while deleting supplier , please try again");

    }
}
