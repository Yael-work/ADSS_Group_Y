package DB.implementations.Suppliers;

import DB.interfaces.Suppliers.IOrderController;
import DTO.Suppliers.OrderDTO;
import DTO.Suppliers.OrderItemDTO;
import Domain.suppliers.IOrdersRepository;
import Domain.suppliers.OrderRepository;
import Models.DBModels.suppliers.Order;
import Models.DBModels.suppliers.OrderItem;

import java.util.ArrayList;

public class MockOrderController implements IOrderController {
    private static IOrdersRepository ordersRepository;
    private static MockOrderController mockOrderController;
    private MockOrderController() {
        ordersRepository = OrderRepository.getInstance();
    }
    public static MockOrderController getInstance() {
        if (mockOrderController == null) {
            mockOrderController = new MockOrderController();
        }
        return mockOrderController;
    }

    @Override
    public OrderDTO createOrder(OrderDTO order) {
        Order result = ordersRepository.createOrder(order) ;
        if(result == null)
            return null;
        return result.toDTO();
    }

    @Override
    public boolean addOrderItem(OrderItemDTO orderItem) {
        Order order = ordersRepository.getOrderById(orderItem.getOrderID()) ;
        if(order == null)
            return false;
        return order.addItem(orderItem.getItemCode() , orderItem.getQuantity() , orderItem.getTotalPrice());
    }

    @Override
    public OrderDTO getOrderById(int id){
        Order result = ordersRepository.getOrderById(id);
        if(result == null)
            return null;
        OrderDTO dto = result.toDTO();
        dto.setId(result.getId());
        return dto;
    }

    @Override
    public boolean deleteOrder(int id) {
        return ordersRepository.deleteOrder(id);
    }

    @Override
    public boolean updateOrder(OrderDTO order) {
        return ordersRepository.updateOrder(order);
    }

    @Override
    public ArrayList<OrderItemDTO> getOrderItems(int orderId) {
        ArrayList<OrderItem> arr = ordersRepository.getAllOrderItems(orderId);
        if(arr == null)
            return null;
        ArrayList<OrderItemDTO> result = new ArrayList<>();
        for(OrderItem oi : arr){
            result.add(new OrderItemDTO(oi.getItemCode() , oi.getQuantity() , oi.getTotalPrice() , orderId)) ;
        }
        return result;
    }

    @Override
    public ArrayList<OrderDTO> getAllOrders() {
        ArrayList<Order> arr = ordersRepository.getAllOrders();
        if(arr == null)
            return null;
        ArrayList<OrderDTO> result = new ArrayList<>();
        for(Order o : arr){
            OrderDTO orderDTO = o.toDTO();
            orderDTO.setId(o.getId());
            result.add(orderDTO);
        }
        return result;
    }
}
