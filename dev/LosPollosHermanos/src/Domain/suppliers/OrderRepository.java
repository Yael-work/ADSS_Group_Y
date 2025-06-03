package Domain.suppliers;

import DB.DAO.Suppliers.OrderDAO;
import DB.DAO.Suppliers.OrderDAO_SQL;
import DB.DAO.Suppliers.OrderItemDAO;
import DB.DAO.Suppliers.OrderItemDAO_SQL;
import DTO.Suppliers.OrderDTO;
import DTO.Suppliers.OrderItemDTO;
import Models.DBModels.suppliers.Contract;
import Models.DBModels.suppliers.Order;
import Models.DBModels.suppliers.OrderItem;
import Models.DBModels.suppliers.Supplier;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderRepository implements IOrdersRepository {
    private static OrderRepository instance;
    private static OrderDAO orderDAO;
    private static OrderItemDAO orderItemDAO;
    private static HashMap<Integer , Order> ordersMap ;

    private OrderRepository() {
        ordersMap = new HashMap<>();
        orderDAO = new OrderDAO_SQL() ;
        orderItemDAO = new OrderItemDAO_SQL() ;
    }
    public static OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }
    @Override
    public boolean addOrderItem(OrderItemDTO orderItem) {
        Order order = getOrderById(orderItem.getOrderID()) ;
        Contract contract = ContractRepository.getInstance().getContractContainsItem(orderItem.getItemCode() , order.getSupplier().getNumber());
        orderItem.setTotalPrice(contract.getTotalPriceOfItems(orderItem.getItemCode() , orderItem.getQuantity()));
        return orderItemDAO.addOrderItem(orderItem);
    }

    @Override
    public ArrayList<OrderItem> getAllOrderItems(int orderId) {
        ArrayList<OrderItemDTO> arr = orderItemDAO.getAllOrderItems(orderId) ;
        if(arr == null)
            return null;
        ArrayList<OrderItem> result = new ArrayList<>() ;
        for(OrderItemDTO item : arr){
            result.add(new OrderItem(item.getItemCode() , item.getQuantity() , item.getTotalPrice())) ;
        }
        return result;
    }

    @Override
    public Order getOrderById(int orderId) {
        if(ordersMap.containsKey(orderId))
            return ordersMap.get(orderId);
        OrderDTO orderDTO = orderDAO.getOrderById(orderId) ;
        if(orderDTO == null)
            return null;
        Supplier supplier = SuppliersRepository.getInstance().getSupplier(orderDTO.getSupplierNumber());
        if(supplier == null)
            return null ;
        Order result = new Order(supplier , orderDTO.getAddress() , orderDTO.getDate() , orderDTO.getPhone() , orderDTO.getStatus() , orderDTO.getId()) ;
        ordersMap.put(orderId, result) ;
        return result;
    }

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        OrderDTO o = orderDAO.addOrder(orderDTO) ;
        if(orderDTO == null)
            return null;
        Supplier supplier = SuppliersRepository.getInstance().getSupplier(orderDTO.getSupplierNumber());
        if(supplier == null)
            return null ;
        Order result = new Order(supplier , o.getAddress() , o.getDate() , o.getPhone() , o.getStatus() , o.getId()) ;
        ordersMap.put(o.getId(), result) ;
        return result;
    }

    @Override
    public boolean deleteOrder(int id) {
        ordersMap.remove(id);
        return orderDAO.deleteOrder(id) ;
    }

    @Override
    public boolean updateOrder(OrderDTO order) {
        boolean r = orderDAO.updateOrder(order);
        if(!r)
            return false;
        Order o = getOrderById(order.getId()) ;
        if(o == null)
            return false;
        ordersMap.put(order.getId() , o) ;
        return orderDAO.updateOrder(order);
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        ArrayList<OrderDTO> arr = orderDAO.getAllOrders();
        if(arr == null)
            return null;
        ArrayList<Order> result = new ArrayList<>() ;
        for(OrderDTO item : arr){
            Supplier supplier = SuppliersRepository.getInstance().getSupplier(item.getSupplierNumber());
            if(supplier == null)
                return null ;
            Order order = new Order(supplier , item.getAddress() , item.getDate() , item.getPhone() , item.getStatus() , item.getId()) ;
            ordersMap.put(item.getId(), order) ;
        }
        return result;
    }
}
