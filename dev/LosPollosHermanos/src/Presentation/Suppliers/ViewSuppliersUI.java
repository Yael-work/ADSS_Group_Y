package Presentation.Suppliers;

import DTO.Suppliers.SupplierDTO;
import Models.DBModels.suppliers.Supplier;
import Services.SuppliersServices;

import java.util.ArrayList;

public class ViewSuppliersUI {
    // Database manager instance to handle DB operations

    /**
     * Constructor for ViewSuppliersUI.
     * Initializes the DBManager which will be used to fetch supplier data.
     */
    public ViewSuppliersUI() {
    }

    /**
     * Retrieves all suppliers from the database and displays them.
     * If no suppliers are found, an appropriate message is shown.
     */
    public void viewAllSuppliers() {
        // Fetch all suppliers from the database
        ArrayList<SupplierDTO> suppliers = SuppliersServices.getAllSuppliers();

        // Check if the list is empty and handle accordingly
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers found");
        } else {
            // Print each supplier with an index for easy reading
            for (int i = 0; i < suppliers.size(); i++) {
                System.out.print(i + 1 + ") ");
                System.out.println(suppliers.get(i));
            }
        }
    }
}
