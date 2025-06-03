package Presentation.Suppliers;

import DTO.Suppliers.OrderDTO;
import Models.DBModels.suppliers.Order;
import Services.OrdersServices;

import java.util.ArrayList;

public class ViewOrdersUI {
    // instance of DBManager to communicate with the database

    /**
     * Constructor for ViewOrdersUI.
     * Initializes the DBManager that will help fetch orders from DB.
     */
    public ViewOrdersUI() {
    }

    /**
     * Fetches and displays all orders from the database.
     * Prints a message if there are no orders to show.
     */
    public void viewAllOrders() {
        // Get all the orders from database
        ArrayList<OrderDTO> orders = OrdersServices.getAllOrders();

        // Check if we even have any orders
        if (orders == null || orders.isEmpty()) {
            System.out.println("No Orders Found");
        } else {
            // Loop through orders and print each one
            for (int i = 0; i < orders.size(); i++) {
                System.out.println("------- order " + i + 1 + " -------");
                System.out.println(orders.get(i));
            }
        }
    }
}
