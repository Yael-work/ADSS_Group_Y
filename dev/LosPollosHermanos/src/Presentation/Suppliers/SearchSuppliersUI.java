package Presentation.Suppliers;

import Auxiliary.SupplierHelper;
import DTO.Suppliers.ContactPersonDTO;
import DTO.Suppliers.SupplierDTO;
import Models.DBModels.suppliers.Supplier;
import Services.SuppliersServices;

import java.util.ArrayList;
import java.util.Scanner;

public class SearchSuppliersUI {
    Scanner in;

    /**
     * Constructor - sets up db manager and scanner.
     */
    public SearchSuppliersUI() {
        in = new Scanner(System.in);
    }

    /**
     * Starts the search menu flow.
     * Lets the user pick how they want to search.
     */
    public void start() {
        System.out.println("--------------Supplier Search Menu----------------");
        printMenu();
        int x = SupplierHelper.getIntegerFromUser("Enter your choice : " , 1 , 2);
        if (x == 1) {
            handleSearchByNumber();
        } else if (x == 2) {
            handleSearchByName();
        }
    }

    /**
     * Prints the search options.
     */
    private void printMenu() {
        System.out.println("1. Search by number");
        System.out.println("2. Search by name");
    }

    /**
     * Handles search flow when user chooses to search by number.
     */
    private void handleSearchByNumber() {
        System.out.print("Enter the supplier's number : ");
        String input = in.nextLine();
        System.out.println();
        SupplierDTO supplier = SuppliersServices.getSupplierByNumber(input);
        if (supplier == null) {
            System.out.println("Supplier not found");
            return;
        }
        ArrayList<ContactPersonDTO> contacts = SuppliersServices.getContacts(supplier.getNumber()) ;
        if(contacts == null){
            System.out.println("something went wrong with sql");
            return;
        }
        System.out.println(supplier);
        System.out.println(SupplierHelper.contactPeopleToString(contacts));
    }

    /**
     * Handles search flow when user chooses to search by name.
     */
    private void handleSearchByName() {
        System.out.print("Enter the supplier's name : ");
        String input = in.nextLine();
        System.out.println();
        ArrayList<SupplierDTO> suppliers = SuppliersServices.getAllSuppliersByName(input);
        if (suppliers.isEmpty()) {
            System.out.println("Supplier not found");
            return;
        }
        for(SupplierDTO supplier : suppliers) {
            ArrayList<ContactPersonDTO> contacts = SuppliersServices.getContacts(supplier.getNumber());
            if (contacts == null) {
                System.out.println("something went wrong with sql");
                return;
            }
            System.out.println(supplier);
            System.out.println(SupplierHelper.contactPeopleToString(contacts));
            System.out.println("---------------------------------------------------");
        }
    }
}
