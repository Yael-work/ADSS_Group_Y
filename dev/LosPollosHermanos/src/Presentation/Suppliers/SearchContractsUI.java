package Presentation.Suppliers;

import Auxiliary.SupplierHelper;
import DTO.Suppliers.ContractDTO;
import DTO.Suppliers.ContractTermDTO;
import DTO.Suppliers.SupplierDTO;
import Models.DBModels.suppliers.Contract;
import Models.DBModels.suppliers.ContractTerm;
import Models.DBModels.suppliers.Supplier;
import Services.ContractsServices;
import Services.SuppliersServices;

import java.util.ArrayList;
import java.util.Scanner;

public class SearchContractsUI {
    Scanner in;

    /**
     * Constructor - initializes the DB manager and scanner for user input.
     */
    public SearchContractsUI() {
        in = new Scanner(System.in);
    }

    /**
     * Starts the contract search flow.
     * Displays a menu and processes user input for searching.
     */
    public void start() {
        System.out.println("--------------Contract Search Menu----------------");
        printMenu();
        int x = SupplierHelper.getIntegerFromUser("Enter your choice : " , 1 , 3);
        if (x == 1) {
            handleSearchBySupplierNumber();
        } else if (x == 2) {
            handleSearchByItemCode();
        } else if(x == 3){
            handleSearchByContractId();
        }
    }

    /**
     * Displays the search options for the user.
     */
    private void printMenu() {
        System.out.println("1. Search By Supplier's Number");
        System.out.println("2. Search By Item's Code");
        System.out.println("3. Search by contract ID");
    }

    private void handleSearchByContractId(){
        int contractId = SupplierHelper.getIntegerFromUser("Enter a contract ID : " , 1);
        ContractDTO contract = ContractsServices.getContractById(contractId);
        if(contract == null){
            System.out.println("contract is not found");
            return;
        }
        ArrayList<ContractTermDTO> terms = ContractsServices.getContractTerms(contractId);
        if(terms == null) {
            System.out.println("something went wrong retrieving contracts terms");
        }else{
            System.out.println(SupplierHelper.contactTermsArrayToString(terms));
        }
    }
    /**
     * Handles searching for contracts by supplier number.
     * Displays all contracts related to the given supplier.
     */
    private void handleSearchBySupplierNumber() {
        String input = SupplierHelper.getNonEmptyStringFromUser("Enter the supplier's number: ");
        System.out.println();
        SupplierDTO supplier = SuppliersServices.getSupplierByNumber(input);
        if (supplier == null) {
            System.out.println("Supplier not found");
            return;
        }
        // Retrieve and print contracts related to the supplier
        ArrayList<ContractDTO> contracts = ContractsServices.getContractsBySupplier(supplier.getNumber());
        if (contracts.isEmpty()) {
            System.out.println("No contracts with this supplier were found.");
        }
        SupplierHelper.printContractList(contracts);
    }

    /**
     * Handles searching for contracts by item code.
     * Displays all contracts that contain the given item.
     */
    private void handleSearchByItemCode() {
        String str = SupplierHelper.getNonEmptyStringFromUser("Enter item Code: ");
        System.out.println();
        ArrayList<ContractDTO> contracts = ContractsServices.getAllContractsContainingItem(str);
        if (contracts.isEmpty()) {
            System.out.println("No contracts with this item were found.");
        }
        // Print out the contracts containing the item code
        SupplierHelper.printContractList(contracts);
    }
}
