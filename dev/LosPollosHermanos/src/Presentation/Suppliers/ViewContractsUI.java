package Presentation.Suppliers;


import Auxiliary.SupplierHelper;
import DTO.Suppliers.ContractDTO;
import DTO.Suppliers.ContractTermDTO;
import Models.DBModels.suppliers.Contract;
import Services.ContractsServices;

import java.util.ArrayList;

/**
 * ViewContractsUI is responsible for showing all contracts to the user.
 * It uses DBManager to fetch the contracts from the database and displays them nicely.
 */
public class ViewContractsUI {

    /**
     * Constructor for ViewContractsUI.
     * Sets up a DBManager so we can later fetch contracts from the DB.
     */
    public ViewContractsUI() {
    }

    /**
     * Fetches and displays all contracts from the database.
     * If no contracts exist, tells the user that none were found.
     */
    public void viewAllContracts() {
        // Grab all contracts from the database
        ArrayList<ContractDTO> contracts = ContractsServices.getAllContracts();

        // If we got nothing, let the user know
        if (contracts.isEmpty()) {
            System.out.println("No Contracts Found");
        } else {
            // Otherwise, print each contract with its number
            SupplierHelper.printContractList(contracts);
        }
    }
}
