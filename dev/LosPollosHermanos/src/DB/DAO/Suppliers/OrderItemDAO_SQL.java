package DB.DAO.Suppliers;

import DB.base.DatabaseSQL;
import DTO.Suppliers.AmountSpecifierDTO;
import DTO.Suppliers.ContractTermDTO;
import DTO.Suppliers.ItemDTO;
import DTO.Suppliers.OrderItemDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderItemDAO_SQL implements OrderItemDAO {
    @Override
    public boolean addOrderItem(OrderItemDTO orderItem) {
        String sql = "INSERT INTO OrderItems(itemCode , quantity , totalPrice , orderId) VALUES(?,?,?,?)";
        try (PreparedStatement ps = DatabaseSQL.getConnection()
                .prepareStatement(sql)) {
            ps.setString(1 , orderItem.getItemCode());
            ps.setInt(2, orderItem.getQuantity());
            ps.setFloat(3, (float) orderItem.getTotalPrice());
            ps.setInt(4,  orderItem.getOrderID());
            ps.executeUpdate();
            return true ;
        } catch (SQLException e) {
            return false ;
        }
    }

    @Override
    public boolean deleteOrderItem(OrderItemDTO orderItem) {
        String sql = "DELETE FROM OrderItems WHERE orderId = ? AND itemCode = ?";

        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)){
            ps.setInt(1, orderItem.getOrderID());
            ps.setString(2, orderItem.getItemCode());
            ps.executeUpdate();
            return true ;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public ArrayList<OrderItemDTO> getAllOrderItems(int orderId) {
        String sql = "SELECT * FROM OrderItems WHERE orderId = ?";
        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)){
            ps.setInt(1 , orderId);
            try(ResultSet rs = ps.executeQuery()) {
                ArrayList<OrderItemDTO> list = new ArrayList<>();
                while (rs.next()) {
                    String itemCode = rs.getString("itemCode");
                    int quantity = rs.getInt("quantity");
                    double totalPrice = rs.getFloat("totalPrice");
                    int id = rs.getInt("orderId");
                    list.add(new OrderItemDTO(itemCode, quantity, totalPrice, id));
                }
                return list;
            }
        } catch (SQLException e) {
            return null ;
        }
    }
}
