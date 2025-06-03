package Presentation.Suppliers;

import Auxiliary.SupplierHelper;
import DTO.Suppliers.OrderDTO;
import Models.DBModels.suppliers.Order;
import Services.OrdersServices;

import java.util.Scanner;

public class OrderDeletionUI {
    // Instance variables for DBManager and Scanner
    private Scanner in;

    /**
     * Constructor initializes the DBManager and Scanner for user input.
     */
    public OrderDeletionUI() {
        in = new Scanner(System.in);
    }

    /**
     * Starts the order deletion process.
     * 1. Prompts the user for an order ID.
     * 2. Searches for the order in the database.
     * 3. If found, deletes the order and confirms the deletion.
     * 4. If not found, informs the user that the order does not exist.
     */
    public void start() {
        System.out.println("--------------Order Deletion form----------------");

        // Get the order ID from the user
        int orderId = SupplierHelper.getIntegerFromUser("Provide the ID of the order you would like to delete : " , 1);

        // Search for the order by ID
        OrderDTO order = OrdersServices.getOrderById(orderId);

        // If the order is not found, inform the user and return
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        // Delete the order if it is found
        if(!OrdersServices.deleteOrder(orderId)){
            System.out.println("something went wrong");
            return;
        }
        System.out.println("Order deleted successfully!");
    }
}
