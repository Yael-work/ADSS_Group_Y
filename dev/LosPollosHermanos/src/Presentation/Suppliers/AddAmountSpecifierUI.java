package Presentation.Suppliers;

import Auxiliary.SupplierHelper;
import DTO.Suppliers.AmountSpecifierDTO;
import DTO.Suppliers.SupplierDTO;
import Models.DBModels.suppliers.AmountSpecifier;
import Models.DBModels.suppliers.Supplier;
import Services.ContractsServices;
import Services.SuppliersServices;

import java.util.Scanner;

public class AddAmountSpecifierUI {
    Scanner in ;

    /**
     * Constructor initializes the DBManager and Scanner for user input.
     */
    public AddAmountSpecifierUI() {
        in = new Scanner(System.in);
    }

    /**
     * Starts the process of adding an amount specifier to a contract.
     * Handles user input and validates the data before updating the contract.
     */
    public void start(){
        // Display header
        System.out.println("--------------Amount Specifier Addition form----------------");

        // Check if there are suppliers in the system
        if(SuppliersServices.getNumberOfSuppliersInDatabase() == 0){
            System.out.println("no suppliers are in the system");
            return;
        }

        // Prompt user to enter item details (code, name)
        System.out.println("Enter the item's code to which you want to add the specifier :");
        String itemCode = SupplierHelper.getNonEmptyStringFromUser("Enter item : ");

        // Prompt user to enter supplier number
        String supplierNumber = SupplierHelper.getNonEmptyStringFromUser("Enter Supplier Number : ");

        // Get supplier by number
        SupplierDTO supp = SuppliersServices.getSupplierByNumber(supplierNumber);
        if(supp == null){
            System.out.println("supplier does not exist");
            return;
        }

        // Get the contract that contains the specified item
        int contractID = ContractsServices.getContractIDContainsItem(itemCode , supp.getNumber());
        if(contractID <= 0){
            System.out.println("there is no such item in one of that supplier's contracts");
            return;
        }

        int minimalAmount = SupplierHelper.getIntegerFromUser("Enter Minimal Amount : " , 0) ;

        // Prompt user to enter base discount for the item
        double baseDiscount = SupplierHelper.getDoubleFromUser("Enter Base Discount : " , 0 , 1) ;

        // Create a new AmountSpecifier object with the entered values
        AmountSpecifierDTO amountSpecifier = new AmountSpecifierDTO(minimalAmount , baseDiscount);

        // Update the contract term with the new amount specifier
        if(!ContractsServices.updateContractTerm(contractID , itemCode , amountSpecifier)){
            System.out.println("something went wrong");
            return ;
        }
        System.out.println("Amount specifier updated");
    }
}
