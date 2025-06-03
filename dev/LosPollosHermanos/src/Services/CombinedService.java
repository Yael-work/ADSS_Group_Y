package Services;

import DB.implementations.Suppliers.MockContractController;
import DB.implementations.Suppliers.MockOrderController;
import DB.interfaces.Suppliers.IContractController;
import DB.interfaces.Suppliers.IOrderController;
import DTO.Suppliers.ItemDTO;
import DTO.Suppliers.OrderDTO;
import DTO.Suppliers.OrderItemDTO;
import DTO.Suppliers.SupplierDTO;

import java.util.ArrayList;

public class CombinedService {
    static IContractController contractController = MockContractController.getInstance() ;
    static IOrderController orderController = MockOrderController.getInstance() ;

    public static boolean doesExistInStock(ItemDTO itemDTO) {
        return true;
    }

    public static SupplierDTO getCheapestSupplierForProduct(String itemCode , int quantity) {
        if(itemCode == null || itemCode.isEmpty() || quantity <= 0)
            return null;
        return contractController.getCheapestSupplierForProduct(itemCode , quantity);
    }

    public static boolean createOrder(OrderDTO orderDTO , ArrayList<OrderItemDTO> list){
        if(orderDTO == null || list == null || list.isEmpty())
            return false;
        OrderDTO result = orderController.createOrder(orderDTO) ;
        if (result == null)
            return false;
        for(OrderItemDTO orderItemDTO : list){
            orderItemDTO.setOrderID(result.getId());
            if(!orderController.addOrderItem(orderItemDTO))
                return false;
        }
        return true;
    }
}
