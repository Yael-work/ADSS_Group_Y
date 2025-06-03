package DB.DAO.Suppliers;

import Auxiliary.EnumConvertor;
import Auxiliary.Enums.Models.Suppliers.OrderStatus;
import DB.base.DatabaseSQL;
import DTO.Suppliers.ContractDTO;
import DTO.Suppliers.OrderDTO;
import DTO.Suppliers.PaymentTermsDTO;
import DTO.Suppliers.SupplierDTO;
import Models.DBModels.suppliers.Date;
import Models.DBModels.suppliers.Order;

import java.sql.*;
import java.util.ArrayList;

public class OrderDAO_SQL implements OrderDAO {
    @Override
    public OrderDTO getOrderById(int orderId) {
        String sql = "SELECT * FROM Orders WHERE id = ?";
        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    String supplierNumber = rs.getString("supplierNumber");
                    String address = rs.getString("address");
                    Date date = new Date(rs.getString("date"));
                    String phone  = rs.getString("phone");
                    OrderStatus status = EnumConvertor.stringToOrderStatus(rs.getString("status")) ;
                    int id = rs.getInt("id");

                    OrderDTO result = new OrderDTO(supplierNumber , address , date , phone , status);
                    result.setId(id);
                    return result;
                }
                return null ;
            }
        } catch (SQLException e) {
            return null ;
        }
    }

    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {
        String sql = "INSERT INTO Orders(supplierNumber , address , date , phone , status) VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = DatabaseSQL.getConnection()
                .prepareStatement(sql , Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, orderDTO.getSupplierNumber());
            ps.setString(2, orderDTO.getAddress());
            ps.setString(3, orderDTO.getDate().toString());
            ps.setString(4, orderDTO.getPhone());
            ps.setString(5, EnumConvertor.enumToString(orderDTO.getStatus()));

            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                keys.next();
                OrderDTO result = new OrderDTO(orderDTO.getSupplierNumber() , orderDTO.getAddress(),
                        orderDTO.getDate() , orderDTO.getPhone() , orderDTO.getStatus());
                result.setId(keys.getInt(1));
                return result;
            }
        } catch (SQLException e) {
            return null ;
        }
    }

    @Override
    public boolean deleteOrder(int orderId) {
        String sql = "DELETE FROM Orders WHERE id = ?";

        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)){
            ps.setInt(1, orderId);
            ps.executeUpdate();
            return true ;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateOrder(OrderDTO order) {
        String sql = "UPDATE Orders SET supplierNumber = ?, address = ?, date = ?, phone = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)) {
            ps.setString(1, order.getSupplierNumber());
            ps.setString(2, order.getAddress());
            ps.setString(3, order.getDate().toString());
            ps.setString(4, order.getPhone());
            ps.setString(5, EnumConvertor.enumToString(order.getStatus()));

            ps.setInt(6, order.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false ;
        }
    }

    @Override
    public ArrayList<OrderDTO> getAllOrders() {
        String sql = "SELECT * FROM Orders";
        try (Statement st = DatabaseSQL.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            ArrayList<OrderDTO> list = new ArrayList<>();
            while (rs.next()) {
                String supplierNumber = rs.getString("supplierNumber");
                String address = rs.getString("address");
                Date date = new Date(rs.getString("date"));
                String phone = rs.getString("phone");
                OrderStatus status = EnumConvertor.stringToOrderStatus(rs.getString("status"));
                int id = rs.getInt("id");

                OrderDTO orderDTO = new OrderDTO(supplierNumber , address , date , phone , status) ;
                orderDTO.setId(id);
                list.add(orderDTO);
            }
            return list;
        } catch (SQLException e) {
            return null ;
        }    }
}
