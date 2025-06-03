package Domain.suppliers;

import DTO.Suppliers.OrderDTO;
import DTO.Suppliers.OrderItemDTO;
import Models.DBModels.suppliers.Order;
import Models.DBModels.suppliers.OrderItem;

import java.util.ArrayList;

public interface IOrdersRepository {
    boolean addOrderItem(OrderItemDTO orderItem);
    ArrayList<OrderItem> getAllOrderItems(int orderId);
    Order getOrderById(int orderId) ;
    Order createOrder(OrderDTO orderDTO);
    boolean deleteOrder(int id) ;
    boolean updateOrder(OrderDTO order) ;
    ArrayList<Order> getAllOrders() ;
}
