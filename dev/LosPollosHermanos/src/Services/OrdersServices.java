package Services;

import DB.implementations.Suppliers.MockOrderController;
import DB.interfaces.Suppliers.IOrderController;
import DTO.Suppliers.OrderDTO;
import DTO.Suppliers.OrderItemDTO;

import java.util.ArrayList;

public class OrdersServices {
    private static OrdersServices instance;
    private static IOrderController orderController = MockOrderController.getInstance();
    private OrdersServices() {}

    public static OrdersServices getInstance() {
        if (instance == null) {
            instance = new OrdersServices();
        }
        return instance;
    }

    public static OrderDTO addOrder(OrderDTO order) {
        if(order == null)
            return null;
        return orderController.createOrder(order) ;
    }

    public static boolean addOrderItem(OrderItemDTO orderItem) {
        if(orderItem == null)
            return false;
        return orderController.addOrderItem(orderItem) ;
    }

    public static OrderDTO getOrderById(int id) {
        if(id < 0)
            return null;
        return orderController.getOrderById(id) ;
    }

    public static boolean deleteOrder(int id) {
        if(id < 0)
            return false;
        return orderController.deleteOrder(id) ;
    }

    public static boolean updateOrder(OrderDTO order) {
        if(order == null)
            return false;
        return orderController.updateOrder(order) ;
    }

    public static ArrayList<OrderItemDTO> getOrderItems(int orderId) {
        if (orderId < 0)
            return null;
        return orderController.getOrderItems(orderId) ;
    }

    public static ArrayList<OrderDTO> getAllOrders() {
        return orderController.getAllOrders() ;
    }
}
