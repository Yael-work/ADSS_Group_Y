package Presentation.Suppliers;

import Auxiliary.SupplierHelper;
import DTO.Suppliers.AmountSpecifierDTO;
import DTO.Suppliers.ContractTermDTO;
import DTO.Suppliers.ItemDTO;
import DTO.Suppliers.SupplierDTO;
import Models.DBModels.suppliers.AmountSpecifier;
import Models.DBModels.suppliers.ContractTerm;
import Models.DBModels.suppliers.Item;
import Models.DBModels.suppliers.Supplier;
import Services.CombinedService;
import Services.ContractsServices;
import Services.SuppliersServices;

import java.util.ArrayList;
import java.util.Scanner;

public class ContractCreationUI {
    Scanner in ;

    public ContractCreationUI(){
        in = new Scanner(System.in);
    }

    /**
     * Starts the contract creation process.
     * This method prompts the user to enter a supplier number and contract terms.
     * It validates inputs and adds the contract to the database if everything is correct.
     */
    public void start(){
        System.out.println("--------------Contract creation form----------------");

        // Check if there are any suppliers in the system
        if(SuppliersServices.getNumberOfSuppliersInDatabase() == 0){
            System.out.println("No suppliers are in the system");
            return;
        }

        SupplierDTO supplier = null ;
        while(supplier == null) {
            String suppNum = SupplierHelper.getNonEmptyStringFromUser("Enter the supplier's number : ");

            // If the user types "exit", skip the rest of the code and start over
            if (suppNum.equalsIgnoreCase("exit"))
                continue;

            // Retrieve the supplier from the database
            supplier = SuppliersServices.getSupplierByNumber(suppNum);

            // If the supplier doesn't exist, inform the user and ask for the input again
            if (supplier == null) {
                System.out.println("Supplier does not exist");
            }
        }

        ArrayList<ContractTermDTO> terms = handleContractTerms(supplier.getNumber());
        // Add the contract to the database
        int contractID = ContractsServices.addContract(supplier.getNumber(), terms);
        if (contractID == -1) {
            System.out.println("Contract creation failed");
            return;
        }
        System.out.println("Contract has been added successfully !");
        System.out.println("Contract ID : " + contractID);
    }

    private boolean isLegalItem(ArrayList<ContractTermDTO> contractTerms , String itemCode){
        if(contractTerms == null || itemCode == null)
            return false;
        for(ContractTermDTO contractTerm : contractTerms){
            if(contractTerm.getItemDTO().getCode().equals(itemCode))
                return false ;
        }
        return true ;
    }

    private ArrayList<ContractTermDTO> handleContractTerms(String supplierNumber){
        ArrayList<ContractTermDTO> contractTerms = new ArrayList<>() ;
        System.out.println("Enter a contract term in the format: itemCode,itemName,price,baseDiscount,minimalAmount");
        System.out.println("When you are done, please enter 'exit'");

        // Loop to input multiple contract terms
        while(true){
            System.out.println("Contract term :");
            String term = in.nextLine();

            // Exit the loop if the user types "exit"
            if(term.equalsIgnoreCase("exit"))
                break ;

            // Split the input into an array
            String[] arr = term.split(",");

            // Validate the input format
            if(arr.length != 5){
                System.out.println("Wrong format");
                continue;
            }

            if("".equals(arr[0]) || "".equals(arr[1]) || "".equals(arr[2]) || "".equals(arr[3]) || "".equals(arr[4])){
                System.out.println("a field found empty , can't accept that , please try again");
                continue;
            }

            ItemDTO itemDTO = new ItemDTO(arr[0] , arr[1]) ;

            // Ensure the item is not already part of another contract with the same supplier
            if(ContractsServices.doesSupplierSupplyItem(supplierNumber , itemDTO)){
                System.out.println("Item already exists in another contract with this supplier");
                continue;
            }

            // Ensure the item has not been entered before in the current contract
            if(!isLegalItem(contractTerms , itemDTO.getCode())){
                System.out.println("You already entered this item code before");
                continue;
            }

            //ensure the item exists in stock system
            if(!CombinedService.doesExistInStock(itemDTO)){
                System.out.println("this item is not familiar by stock , please notify stock");
                continue;
            }

            // Parse and validate price
            double price = 0;
            try {
                price = Double.parseDouble(arr[2]);
                if(price <= 0){
                    System.out.println("Price must be greater than 0");
                    continue;
                }
            }catch(NumberFormatException ex){
                System.out.println("The price provided is not a number");
                continue;
            }

            // Parse and validate base discount
            double baseDiscount = 0;
            try {
                baseDiscount = Double.parseDouble(arr[3]);
                if(baseDiscount < 0 || baseDiscount >= 1){
                    System.out.println("Base discount must be greater than 0 and smaller than 1");
                    continue;
                }
            }catch(NumberFormatException ex){
                System.out.println("The baseDiscount provided is not a number");
                continue;
            }

            // Parse and validate minimal amount
            int minimalAmount = 0;
            try {
                minimalAmount = Integer.parseInt(arr[4]);
                if(minimalAmount <= 0){
                    System.out.println("Minimum amount must be greater than 0");
                    continue;
                }
            }catch(NumberFormatException ex){
                System.out.println("The minimal amount provided is not a number");
                continue;
            }

            // Create a new contract term
            ContractTermDTO term1 = new ContractTermDTO(itemDTO , new AmountSpecifierDTO(minimalAmount , baseDiscount) , price , -1) ;
            contractTerms.add(term1) ;
        }

        return contractTerms ;
    }
}
