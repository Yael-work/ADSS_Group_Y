package Presentation.Suppliers;

import Auxiliary.EnumConvertor;
import Auxiliary.Enums.Models.Suppliers.*;
import Auxiliary.SupplierHelper;

import java.util.ArrayList;
import java.util.Scanner;

import DTO.Suppliers.ContactPersonDTO;
import DTO.Suppliers.PaymentTermsDTO;
import DTO.Suppliers.SupplierDTO;
import Models.DBModels.suppliers.*;
import Services.SuppliersServices;

public class SupplierCreationUI {
    Scanner in;
    String name;
    String address;
    String phone;
    String number;
    PaymentTermsDTO paymentTerms;
    ArrayList<ContactPersonDTO> contacts;
    SupplierDTO supplierDTO ;

    /**
     * Constructor - setups up Scanner and DB connection
     */
    public SupplierCreationUI() {
        in = new Scanner(System.in);
    }

    /**
     * Starts the supplier creation process.
     * Goes through several steps to collect supplier info.
     */
    public void start() {
        in = new Scanner(System.in);
        System.out.println("--------------Supplier creation form----------------");

        fillBasicDetails();

        // Check if supplier already exists
        if (SuppliersServices.getSupplierByNumber(number) != null) {
            System.out.println("Supplier's Number Already Exists");
            return;
        }

        // More detailed info
        paymentTerms = handlePaymentTerms();
        contacts = handleContactPeople(number);
        supplierDTO = new SupplierDTO(name , address , phone , number , paymentTerms , SupplierType.constant , null);
        askForSupplierType();
    }

    private void fillBasicDetails() {
        // Basic info
        name = SupplierHelper.getNonEmptyStringFromUser("Enter Supplier Name : ");
        address = SupplierHelper.getNonEmptyStringFromUser("Enter Supplier Address : ");
        phone = SupplierHelper.getNonEmptyStringFromUser("Enter Supplier Phone : ");
        number = SupplierHelper.getNonEmptyStringFromUser("Enter Supplier Number : ");
    }

    /**
     * Handles input for adding contact people
     *
     * @return list of ContactPerson
     */
    private ArrayList<ContactPersonDTO> handleContactPeople(String suppNumber) {
        ArrayList<ContactPersonDTO> contacts = new ArrayList<>();
        System.out.println("please fill a list of contact people , if you want to stop enter exit :");
        while (true) {
            String name = SupplierHelper.getNonEmptyStringFromUser("Enter Contact Person Name : ");
            if (name.equalsIgnoreCase("exit")) {
                return contacts;
            }
            String phone = SupplierHelper.getNonEmptyStringFromUser("Enter Contact Person Phone : ");
            if (phone.equalsIgnoreCase("exit")) {
                return contacts;
            }
            contacts.add(new ContactPersonDTO(name, phone , suppNumber));
            System.out.println("-------Contact" + contacts.size() + " have been added-------");
        }
    }

    /**
     * Handles input for payment terms (payment method + delivery time)
     *
     * @return PaymentTerms object
     */
    private PaymentTermsDTO handlePaymentTerms() {
        PaymentDeliveryTime paymentDeliveryTime = SupplierHelper.getPaymentDeliveryTimeFromUser();
        PaymentMethod method = SupplierHelper.getPaymentMethodFromUser();
        String bankDetails = SupplierHelper.getNonEmptyStringFromUser("Enter your bank details : ");

        return new PaymentTermsDTO(method, paymentDeliveryTime, bankDetails);
    }

    /**
     * Asks what kind of supplier it is and handles accordingly
     *
     * @return Supplier object
     */
    private void askForSupplierType() {
        while (true) {
            System.out.println("Pick Supplier Type :");
            System.out.println("1. Constant supply days");
            System.out.println("2. whenever an order comes in");
            int type = SupplierHelper.getIntegerFromUser("Enter your choice : ", 1, 2);
            if (type == 1) {
                handleConstantSupplier();
                return;
            } else if (type == 2) {
                handleNonConstantSupplier();
                return;
            }
        }
    }

    /**
     * Handles creation of a ConstantSupplier (fixed delivery days)
     *
     * @return ConstantSupplier
     */
    private void handleConstantSupplier() {
        ArrayList<DeliveryDays> deliveryDays = SupplierHelper.getDeliveryDaysFromUser();
        supplierDTO.setType(SupplierType.constant);
        supplierDTO.setDeliveryDays(deliveryDays);

        if (!SuppliersServices.addSupplier(supplierDTO))
            System.out.println("Couldn't add supplier , please try again");
        else {
            System.out.println("Supplier added Successfully !");
            if(!SuppliersServices.addContactPeople(contacts))
                System.out.println("Couldn't add contact people , please try again");
        }
    }

    /**
     * Handles creation of a NonConstantSupplier
     *
     * @return NonConstantSupplier object
     */
    private void handleNonConstantSupplier() {
        supplierDTO.setType(SupplierType.nonConstant);
        supplierDTO.setDeliveryDays(new ArrayList<>());

        if (!SuppliersServices.addSupplier(supplierDTO))
            System.out.println("Couldn't add supplier , please try again");
        else {
            System.out.println("Supplier added Successfully !");
            if(!SuppliersServices.addContactPeople(contacts))
                System.out.println("Couldn't add contact people , please try again");
        }
    }
}
