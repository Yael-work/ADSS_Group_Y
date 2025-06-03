package Presentation.Suppliers;

import Auxiliary.EnumConvertor;
import Auxiliary.Enums.Models.Suppliers.OrderStatus;
import Auxiliary.SupplierHelper;
import DTO.Suppliers.OrderDTO;
import Models.DBModels.suppliers.Order;
import Services.OrdersServices;

import java.util.Scanner;

public class UpdateOrderStatusUI {
    // DBManager to access the database
    // Scanner to read user input
    Scanner in;

    /**
     * Constructor for UpdateOrderStatusUI.
     * Initializes the DBManager and Scanner needed for operations.
     */
    public UpdateOrderStatusUI() {
        in = new Scanner(System.in);
    }

    /**
     * Starts the update order status flow.
     * Asks user for an order id and the new status they want to set.
     */
    public void start() {
        System.out.println("--------------Update Order Status Form----------------");

        // Get the order id from user
        int x = SupplierHelper.getIntegerFromUser("provide the id of the order you would like to update it's status : " , 1);
        OrderDTO order = OrdersServices.getOrderById(x);

        // Check if the order exists
        if (order == null) {
            System.out.println("Order not found");
            return;
        }

        // Update the order's status
        order.setStatus(handleStatus());
        if(OrdersServices.updateOrder(order))
            System.out.println("Order updated successfully !");
        else
            System.out.println("Order update failed");
    }

    /**
     * Helper method to get the new order status from the user.
     * Shows the user the available options and gets valid input.
     *
     * @return the selected OrderStatus
     */
    private OrderStatus handleStatus() {
        System.out.println("----- Status menu -----");
        EnumConvertor.printOrderStatusMenu();

        int x = SupplierHelper.getIntegerFromUser("Enter your choice : " , 1 , 3);
        return EnumConvertor.integerToOrderStatus(x);
    }
}