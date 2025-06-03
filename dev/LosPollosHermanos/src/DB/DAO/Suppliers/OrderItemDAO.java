package DB.DAO.Suppliers;

import DTO.Suppliers.OrderDTO;
import DTO.Suppliers.OrderItemDTO;
import Models.DBModels.suppliers.Order;
import Models.DBModels.suppliers.OrderItem;

import java.util.ArrayList;

public interface OrderItemDAO {
    boolean addOrderItem(OrderItemDTO orderItem) ;
    boolean deleteOrderItem(OrderItemDTO orderItem) ;
    ArrayList<OrderItemDTO> getAllOrderItems(int orderId) ;
}
