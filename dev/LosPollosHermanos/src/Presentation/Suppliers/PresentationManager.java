package Presentation.Suppliers;

import Auxiliary.Enums.Models.Suppliers.PaymentDeliveryTime;
import Auxiliary.Enums.Models.Suppliers.PaymentMethod;
import Auxiliary.Enums.Models.Suppliers.SupplierType;
import Auxiliary.SupplierHelper;
import DTO.Suppliers.ContactPersonDTO;
import DTO.Suppliers.PaymentTermsDTO;
import DTO.Suppliers.SupplierDTO;
import Services.SuppliersServices;

import java.util.ArrayList;

public class PresentationManager {

    public static void start(){
        while(true) {
            printMenu();
            int choice = SupplierHelper.getIntegerFromUser("Enter your choice : " , 1 , 15) ;
            switch (choice){
                case 1 -> {
                    SupplierCreationUI supplierCreationUI = new SupplierCreationUI();
                    supplierCreationUI.start();
                }case 2 -> {
                    SupplierEditUI supplierEditUI = new SupplierEditUI();
                    supplierEditUI.start();
                }case 3 -> {
                    ViewSuppliersUI viewSuppliersUI = new ViewSuppliersUI();
                    viewSuppliersUI.viewAllSuppliers() ;
                }case 4 -> {
                    SearchSuppliersUI searchSuppliersUI = new SearchSuppliersUI();
                    searchSuppliersUI.start();
                }case 5 -> {
                    SupplierDeletionUI supplierDeletionUI = new SupplierDeletionUI();
                    supplierDeletionUI.start();
                }case 6 -> {
                    ContractCreationUI contractCreationUI = new ContractCreationUI();
                    contractCreationUI.start();
                }case 7 -> {
                    ViewContractsUI viewContractsUI = new ViewContractsUI();
                    viewContractsUI.viewAllContracts() ;
                }case 8 -> {
                    SearchContractsUI searchContractsUI = new SearchContractsUI();
                    searchContractsUI.start();
                }case 9 -> {
                    AddAmountSpecifierUI addAmountSpecifierUI = new AddAmountSpecifierUI();
                    addAmountSpecifierUI.start();
                }case 10 -> {
                    AddNewOrderUI addNewOrderUI = new AddNewOrderUI();
                    addNewOrderUI.start();
                }case 11 -> {
                    SearchOrdersUI searchOrdersUI = new SearchOrdersUI();
                    searchOrdersUI.start();
                }case 12 -> {
                    UpdateOrderStatusUI updateOrderStatusUI = new UpdateOrderStatusUI();
                    updateOrderStatusUI.start();
                }case 13 -> {
                    OrderDeletionUI orderDeletionUI = new OrderDeletionUI();
                    orderDeletionUI.start();
                }case 14 -> {
                    ViewOrdersUI viewOrdersUI = new ViewOrdersUI();
                    viewOrdersUI.viewAllOrders() ;
                }default -> {return;}
            }
        }

    }

    private static void printMenu(){
        System.out.println("1. Create a new supplier");
        System.out.println("2. Update supplier details");
        System.out.println("3. Display all supplier details");
        System.out.println("4. Search suppliers");
        System.out.println("5. Delete a supplier");
        System.out.println("6. Create a new contract");
        System.out.println("7. View all contract details");
        System.out.println("8. Search contracts");
        System.out.println("9. Update an amount specifier");
        System.out.println("10. Add new order");
        System.out.println("11. Search orders");
        System.out.println("12. Update order status");
        System.out.println("13. Delete order");
        System.out.println("14. View all orders");
        System.out.println("15. Exit");
    }

    public static void startWithData(){
        PaymentTermsDTO paymentTerms1 = new PaymentTermsDTO(PaymentMethod.cash , PaymentDeliveryTime.afterOrder , "bank1") ;
        SuppliersServices.addSupplier(new SupplierDTO("supplier1" , "address1" , "0581" , "1" , paymentTerms1 , SupplierType.constant , new ArrayList<>())) ;
        for(int i = 0 ; i < 10 ; i ++){
            ContactPersonDTO contactPersonDTO = new ContactPersonDTO("name" + (i + 1) , "phone" + (i + 1) , "1") ;
            ArrayList<ContactPersonDTO> arr = new ArrayList<>();
            arr.add(contactPersonDTO);
            SuppliersServices.addContactPeople(arr) ;
        }
//
//        int contractID1 = ContractsManager.addContract(SuppliersManager.getSupplierByNumber("1")) ;
//        int contractID2 = ContractsManager.addContract(SuppliersManager.getSupplierByNumber("1")) ;
//        ArrayList<ContractTerm> terms = new ArrayList<>() ;
//        for(int i = 0 ; i < 10 ; i ++){
//            Item item = new Item("" + i , "item" + i) ;
//            AmountSpecifier amountSpecifier = new AmountSpecifier(10*i , 0.2) ;
//            terms.add(new ContractTerm(item , (double)100/i , amountSpecifier)) ;
//        }
//        ContractsManager.addContractTermsToContract(contractID1 , terms) ;

        start();
    }
}
