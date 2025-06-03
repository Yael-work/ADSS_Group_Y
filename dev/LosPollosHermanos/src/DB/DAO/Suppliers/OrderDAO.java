package DB.DAO.Suppliers;

import DTO.Suppliers.OrderDTO;
import Models.DBModels.suppliers.Order;

import java.util.ArrayList;

public interface OrderDAO {
    OrderDTO getOrderById(int orderId) ;
    OrderDTO addOrder(OrderDTO orderDTO) ;
    boolean deleteOrder(int orderId) ;
    boolean updateOrder(OrderDTO order) ;
    ArrayList<OrderDTO> getAllOrders() ;
}
