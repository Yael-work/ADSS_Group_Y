package DB.interfaces.Suppliers;

import DTO.Suppliers.OrderDTO;
import DTO.Suppliers.OrderItemDTO;

import java.util.ArrayList;

public interface IOrderController {
    OrderDTO createOrder(OrderDTO order) ;
    boolean addOrderItem(OrderItemDTO orderItem) ;
    OrderDTO getOrderById(int id) ;
    boolean deleteOrder(int id) ;
    boolean updateOrder(OrderDTO order) ;
    ArrayList<OrderItemDTO> getOrderItems(int orderId) ;
    ArrayList<OrderDTO> getAllOrders() ;
}
