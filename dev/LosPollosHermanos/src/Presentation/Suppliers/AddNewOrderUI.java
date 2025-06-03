package Presentation.Suppliers;

import Auxiliary.Enums.Models.Suppliers.OrderStatus;
import Auxiliary.SupplierHelper;
import DTO.Suppliers.OrderDTO;
import DTO.Suppliers.OrderItemDTO;
import DTO.Suppliers.SupplierDTO;
import Models.DBModels.suppliers.*;
import Services.ContractsServices;
import Services.OrdersServices;
import Services.SuppliersServices;

import java.util.ArrayList;
import java.util.Scanner;

public class AddNewOrderUI {
    Scanner in ;

    public AddNewOrderUI() {
        in = new Scanner(System.in);
    }

    public void start(){
        // Printing the order creation header
        System.out.println("--------------Order creation form----------------");

        // Checking if there are suppliers in the system
        if(SuppliersServices.getNumberOfSuppliersInDatabase() == 0){
            System.out.println("no suppliers are in the system");
            return;
        }

        // Asking for the supplier number
        String supNumber = SupplierHelper.getNonEmptyStringFromUser("Enter the Number of the Supplier you want to order from : ");

        // Getting the supplier by the number entered
        SupplierDTO supplier = SuppliersServices.getSupplierByNumber(supNumber);
        if(supplier == null){
            System.out.println("Supplier Not Found");
            return;
        }

        // Asking for the order address
        String address = SupplierHelper.getNonEmptyStringFromUser("Enter the Order Address : ");

        // Asking for the order date and validating the input
        Date date = null ;
        while(true) {
            String str = SupplierHelper.getNonEmptyStringFromUser("Enter the Order Date , in the format YYYY-MM-DD : ");
            String[] arr = str.split("-");
            if (arr.length != 3) {
                System.out.println("Wrong Format");
                continue;
            }
            int day , month, year;
            try{
                year = Integer.parseInt(arr[0]);
                month = Integer.parseInt(arr[1]);
                day = Integer.parseInt(arr[2]);
                if(day <= 0 || day > 31 || month <= 0 || month > 12 || year <= 0){
                    System.out.println("you didn't enter a valid date");
                    continue;
                }
            }catch (Exception e){
                System.out.println("you didn't enter a valid date");
                continue;
            }
            date = new Date(year, month, day);
            break;
        }

        // Asking for the contact number for the order
        String contact = SupplierHelper.getNonEmptyStringFromUser("Enter the Order's contact number : ");

        // Adding the order to the DB and showing the order ID
        OrderDTO toAdd = new OrderDTO(supplier.getNumber() , address , date , contact , OrderStatus.submitted) ;
        OrderDTO order = OrdersServices.addOrder(toAdd) ;
        if(order == null){
            System.out.println("something went wrong");
            return;
        }

        // Handling the item list for the order
        handleItemList(order.getId() , order.getSupplierNumber());
        System.out.println("Order ID : " + order.getId()) ;
        System.out.println("Order added successfully");
    }

    // Method to handle adding items to the order
    private void handleItemList(int orderId , String supplierNumber){
        ArrayList<OrderItemDTO> orderItems = new ArrayList<>();
        System.out.println("enter item and quantity for each item you want to add and when you are done enter 'exit' : itemCode,quantity ");

        // Looping through item entries until the user enters 'exit'
        while(true) {
            String input = SupplierHelper.getNonEmptyStringFromUser("Enter item : ");
            if(input.equals("exit")){
                break;
            }
            String[] arr = input.split(",");
            if(arr.length != 2){
                System.out.println("Wrong Format");
                continue;
            }
            String itemCode = arr[0];
            int quantity = -1;
            try{
                quantity = Integer.parseInt(arr[1]);
                if(quantity <= 0){
                    System.out.println("you didn't enter a valid quantity");
                    continue;
                }
            }catch (Exception e){
                System.out.println("Wrong Format");
                continue;
            }

            // Checking if the item exists in the supplier's contract
            int contractID = ContractsServices.getContractIDContainsItem(itemCode, supplierNumber) ;
            if (contractID <= 0) {
                System.out.println("there is no contract with this supplier that include this item");
                continue;
            }

            double price = ContractsServices.getPriceOfItem(contractID , itemCode , quantity);
            if(price <= 0){
                System.out.println("something went wrong");
                continue;
            }
            // Showing the price and asking for confirmation to add it
            System.out.println("price of this item is " + price + " enter ok to add it , anything else for dismissing it");
            input = in.nextLine() ;
            if(input.equals("ok")) {
                if(!OrdersServices.addOrderItem(new OrderItemDTO(itemCode , quantity , price , orderId))){
                    System.out.println("something went wrong");
                    continue;
                }
            }
        }
    }
}

