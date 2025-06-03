package Presentation.Suppliers;

import Auxiliary.SupplierHelper;
import DTO.Suppliers.OrderDTO;
import DTO.Suppliers.OrderItemDTO;
import Models.DBModels.suppliers.Order;
import Services.OrdersServices;

import java.util.ArrayList;
import java.util.Scanner;

public class SearchOrdersUI {
    Scanner in;

    /**
     * Constructor - sets up DB manager and scanner for user input.
     */
    public SearchOrdersUI() {
        in = new Scanner(System.in);
    }

    /**
     * Starts the order search flow.
     * Shows menu and reacts to user choice.
     */
    public void start() {
        System.out.println("--------------Order Search Menu----------------");
        printMenu();
        int x = SupplierHelper.getIntegerFromUser("Enter your choice: " , 1 , 1);
        if (x == 1) {
            handleSearchById();
        }
    }

    /**
     * Prints out available search options.
     */
    private void printMenu() {
        System.out.println("1. Search by id");
    }

    /**
     * Handles the flow for searching an order by ID.
     */
    private void handleSearchById() {
        System.out.println("Provide the ID of the order you would like to search:");
        int x = SupplierHelper.getIntegerFromUser("Enter order's number : " , 1);
        OrderDTO order = OrdersServices.getOrderById(x);
        if (order == null) {
            System.out.println("Order not found");
            return;
        }
        ArrayList<OrderItemDTO> orderItemDTOS = OrdersServices.getOrderItems(order.getId()) ;
        SupplierHelper.printOrderItemList(orderItemDTOS) ;
    }
}
