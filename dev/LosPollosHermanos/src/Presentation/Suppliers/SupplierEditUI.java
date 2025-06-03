package Presentation.Suppliers;

import Auxiliary.EnumConvertor;
import Auxiliary.Enums.Models.Suppliers.PaymentDeliveryTime;
import Auxiliary.Enums.Models.Suppliers.PaymentMethod;
import Auxiliary.SupplierHelper;
import DTO.Suppliers.ContactPersonDTO;
import DTO.Suppliers.PaymentTermsDTO;
import DTO.Suppliers.SupplierDTO;
import Services.SuppliersServices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SupplierEditUI {
    // DBManager instance to communicate with the database
    // Scanner to read user input
    Scanner in;

    /**
     * Constructor for SupplierEditUI.
     * Initializes DBManager and Scanner for later use.
     */
    public SupplierEditUI() {
        in = new Scanner(System.in);
    }

    /**
     * Starts the supplier edit flow.
     * User picks a supplier and can update its fields.
     */
    public void start() {
        System.out.println("--------------Supplier Edit Form----------------");

        // Check if there are any suppliers to edit
        if (SuppliersServices.getNumberOfSuppliersInDatabase() == 0) {
            System.out.println("no suppliers are in the system");
            return;
        }

        String supNumber = SupplierHelper.getNonEmptyStringFromUser("Enter the Number of the Supplier you want to edit : ") ;
        SupplierDTO supplier = SuppliersServices.getSupplierByNumber(supNumber);

        // Check if supplier exists
        if (supplier == null) {
            System.out.println("Supplier Not Found");
            return;
        }

        System.out.println("if you want to skip a field , simply dont type anything and click enter");

        // Edit name if needed
        System.out.println("previous name <" + supplier.getName() + "> enter new name : ");
        String newName = in.nextLine();
        if (newName != null && !newName.equalsIgnoreCase(""))
            supplier.setName(newName);

        // Edit address if needed
        System.out.println("previous address <" + supplier.getAddress() + "> enter new address : ");
        String newAddress = in.nextLine();
        if (newAddress != null && !newAddress.equalsIgnoreCase(""))
            supplier.setAddress(newAddress);

        // Edit address if needed
        System.out.println("previous phone <" + supplier.getPhone() + "> enter new phone : ");
        String newPhone = in.nextLine();
        if (newPhone != null && !newPhone.equalsIgnoreCase(""))
            supplier.setPhone(newPhone);

        // Update payment terms
        System.out.println("previous payment terms <" + supplier.getPaymentTerms().toString() + "> : ");
        supplier.setPaymentTerms(handlePaymentTerms(supplier.getPaymentTerms()));

        if(!SuppliersServices.updateSupplier(supplier)){
            System.out.println("something went wrong , please try again");
            return ;
        }

        // Update contact people
        if(!handleContactPeopleDeletion(supplier))
            return ;
        if(!handleContactPeopleAddition(supplier))
            return ;

        System.out.println("supplier edited successfully");
    }

    /**
     * Handles updating the payment terms of the supplier.
     * Lets user choose new payment method and delivery time.
     *
     * @param paymentTerms current payment terms
     * @return updated PaymentTerms object
     */
    private PaymentTermsDTO handlePaymentTerms(PaymentTermsDTO paymentTerms) {
        in = new Scanner(System.in);
        PaymentDeliveryTime paymentDeliveryTime = paymentTerms.getPaymentDeliveryTime();
        PaymentMethod method = paymentTerms.getPaymentMethod();

        System.out.println("Pick Payment Method : ");
        EnumConvertor.printPaymentMethodMenu();
        System.out.print("Enter your choice : ");
        String input = in.nextLine();
        if (input != null && !input.equalsIgnoreCase("")) {
            int x ;
            try {
                x = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("you didn't enter a number");
                x = SupplierHelper.getIntegerFromUser("Enter your choice : " , 1 , 3) ;
            }
            method = EnumConvertor.integerToPaymentMethod(x);
        }

        System.out.println("Pick Payment delivery time : ");
        EnumConvertor.printPaymentDeliveryTimeMenu();
        System.out.print("Enter your choice : ");
        input = in.nextLine();
        if (input != null && !input.equalsIgnoreCase("")) {
            int x ;
            try {
                x = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("you didn't enter a number");
                x = SupplierHelper.getIntegerFromUser("Enter your choice : " , 1 , 3) ;
            }
            paymentDeliveryTime = EnumConvertor.integerToPaymentDeliveryTime(x);
        }

        System.out.println("Enter your bank details : ");
        String bankDetails = in.nextLine();
        if (bankDetails == null || bankDetails.equalsIgnoreCase(""))
            bankDetails = paymentTerms.getBankDetails();

        return new PaymentTermsDTO(method, paymentDeliveryTime, bankDetails);
    }

    /**
     * Handles updating the supplier's contact people list.
     * User can remove old contacts and add new ones.
     *
     * @param supplier the supplier to update
     */
    private boolean handleContactPeopleAddition(SupplierDTO supplier) {

        System.out.println("to add a new contact please insert the data in the format : name,phone");
        System.out.println("to stop enter 'exit'");
        ArrayList<ContactPersonDTO> contactPeople = new ArrayList<>() ;
        while (true) {
            System.out.print("Enter contact : ");
            String input = in.nextLine();
            if (input.equalsIgnoreCase("exit"))
                break;

            String[] arr = input.split(",");
            if (arr.length != 2) {
                System.out.println("Wrong format");
                continue;
            }
            ContactPersonDTO person = new ContactPersonDTO(arr[0], arr[1] , supplier.getNumber());
            if(SuppliersServices.doesExist(person)){
                System.out.println("Contact person already exists");
                continue;
            }
            contactPeople.add(person);
        }
        if(!SuppliersServices.addContactPeople(contactPeople)) {
            System.out.println("something went wrong , please try again");
            return false ;
        }
        return true ;
    }

    private boolean handleContactPeopleDeletion(SupplierDTO supplier) {
        ArrayList<ContactPersonDTO> tmp = SuppliersServices.getContacts(supplier.getNumber()) ;
        if(tmp == null) {
            System.out.println("failed to retrieve contacts");
            return false ;
        }
        if (tmp.isEmpty()){
            System.out.println("no contacts found");
            return true ;
        }

        System.out.println("the list of contact people will appear , please pick which you want to remove by entering numbers seperated by ',' : ");
        System.out.println(SupplierHelper.contactPeopleToString(tmp));

        int[] numbers ;
        while(true) {
            System.out.print("Enter your list : ");
            String str = in.nextLine();
            if (str == null || str.equalsIgnoreCase(""))
                return true;

            String[] arr = str.split(",");
            numbers = new int[arr.length];

            boolean flag = false;
            for (int i = 0; i < arr.length; i++) {
                try {
                    numbers[i] = Integer.parseInt(arr[i]) - 1;
                } catch (Exception e) {
                    System.out.println("you entered an illegal number");
                    flag = true;
                    break;
                }
            }
            if (flag) {
                continue;
            }
            break;
        }
        Arrays.sort(numbers);
        ArrayList<ContactPersonDTO> toRemove = new ArrayList<>() ;
        int count = 0 ;
        for(int i = 0 ; i < tmp.toArray().length ; i ++){
            if(i == numbers[count]){
                count ++ ;
                toRemove.add(tmp.get(i));
                if(count >= numbers.length)
                    break;
            }
        }
        if(!SuppliersServices.removeContactPeople(toRemove)) {
            System.out.println("something went wrong , please try again");
            return false;
        }
        return true ;
    }
}
