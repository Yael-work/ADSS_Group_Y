package DB.DAO.Suppliers;

import Auxiliary.EnumConvertor;
import Auxiliary.Enums.Models.Suppliers.PaymentDeliveryTime;
import Auxiliary.Enums.Models.Suppliers.PaymentMethod;
import Auxiliary.Enums.Models.Suppliers.SupplierType;
import DB.base.DatabaseSQL;
import DTO.Suppliers.PaymentTermsDTO;
import DTO.Suppliers.SupplierDTO;
import Models.DBModels.suppliers.PaymentTerms;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO_SQL implements SupplierDAO {
    @Override
    public boolean addSupplier(SupplierDTO supplier) {
        String sql = "INSERT INTO Suppliers(name, address, phone, number, paymentTerms, deliveryDays, type) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement ps = DatabaseSQL.getConnection()
                .prepareStatement(sql)) {
            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getAddress());
            ps.setString(3, supplier.getPhone());
            ps.setString(4, supplier.getNumber());
            ps.setString(5, supplier.getPaymentTerms().toString());
            ps.setString(6, EnumConvertor.deliveryDaysToString(supplier.getDeliveryDays()));
            ps.setString(7, EnumConvertor.enumToString(supplier.getType()));

            ps.executeUpdate();
            return true ;
        } catch (SQLException e) {
            return false ;
        }
    }

    @Override
    public SupplierDTO getSupplier(String number) {
        String sql = "SELECT name, address, phone, number, paymentTerms, deliveryDays, type FROM Suppliers WHERE number = ?";
        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)) {
            ps.setString(1, number);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    String n = rs.getString("number");
                    String paymentTerms = rs.getString("paymentTerms");
                    String deliveryDays = rs.getString("deliveryDays");
                    String type = rs.getString("type");
                    return new SupplierDTO(name , address , phone , n ,
                            new PaymentTermsDTO(paymentTerms) , EnumConvertor.stringToSupplierType(type),
                            EnumConvertor.stringToDeliveryDaysArray(deliveryDays));
                }
                return null ;
            }
        } catch (SQLException e) {
            return null ;
        }
    }

    @Override
    public boolean deleteSupplier(String number) {
        String sql = "DELETE FROM Suppliers WHERE number = ?";

        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)){

            ps.setString(1, number);
            ps.executeUpdate();
            return true ;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplier) {
        String sql = "UPDATE Suppliers SET name = ?, address = ?, phone = ?, paymentTerms = ?, deliveryDays = ?, type = ? WHERE number = ?";
        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)) {
            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getAddress());
            ps.setString(3, supplier.getPhone());
            ps.setString(4, supplier.getPaymentTerms().toString());
            ps.setString(5, EnumConvertor.deliveryDaysToString(supplier.getDeliveryDays()));
            ps.setString(6, EnumConvertor.enumToString(supplier.getType()));

            ps.setString(7, supplier.getNumber());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false ;
        }
    }

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() {
        String sql = "SELECT name, address, phone, number, paymentTerms, deliveryDays, type FROM Suppliers";
        try (Statement st = DatabaseSQL.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            ArrayList<SupplierDTO> list = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String number = rs.getString("number");
                String paymentTerms = rs.getString("paymentTerms");
                String deliveryDays = rs.getString("deliveryDays");
                String type = rs.getString("type");
                list.add(new SupplierDTO(name , address , phone , number ,
                        new PaymentTermsDTO(paymentTerms) , EnumConvertor.stringToSupplierType(type),
                        EnumConvertor.stringToDeliveryDaysArray(deliveryDays)));
            }
            return list;
        } catch (SQLException e) {
            return null ;
        }
    }

    @Override
    public ArrayList<SupplierDTO> getAllSuppliersByName(String name) {
        String sql = "SELECT name, address, phone, number, paymentTerms, deliveryDays, type FROM Suppliers WHERE name = ?";
        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<SupplierDTO> list = new ArrayList<>();
                while(rs.next()) {
                    String n = rs.getString("name");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    String number = rs.getString("number");
                    String paymentTerms = rs.getString("paymentTerms");
                    String deliveryDays = rs.getString("deliveryDays");
                    String type = rs.getString("type");
                    list.add(new SupplierDTO(n , address , phone , number ,
                            new PaymentTermsDTO(paymentTerms) , EnumConvertor.stringToSupplierType(type),
                            EnumConvertor.stringToDeliveryDaysArray(deliveryDays)));
                }
                return list ;
            }
        } catch (SQLException e) {
            return null ;
        }
    }
}
