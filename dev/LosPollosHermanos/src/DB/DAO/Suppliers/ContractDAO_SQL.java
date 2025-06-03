package DB.DAO.Suppliers;

import Auxiliary.EnumConvertor;
import DB.base.DatabaseSQL;
import DTO.Suppliers.ContractDTO;
import DTO.Suppliers.PaymentTermsDTO;
import DTO.Suppliers.SupplierDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ContractDAO_SQL implements ContractDAO {
    @Override
    public ContractDTO addNewContract(String supplierNumber) {
        String sql = "INSERT INTO Contracts(supplierNumber) VALUES(?)";
        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql , Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, supplierNumber);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                keys.next();
                return new ContractDTO(keys.getInt(1), supplierNumber);
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public ContractDTO getContract(int contractId) {
        String sql = "SELECT id , supplierNumber FROM Contracts WHERE id = ?";
        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)) {
            ps.setInt(1, contractId);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    int id = rs.getInt("id");
                    String supplierNumber = rs.getString("supplierNumber");
                    return new ContractDTO(id, supplierNumber);
                }
                return null ;
            }
        } catch (SQLException e) {
            return null ;
        }
    }

    @Override
    public ArrayList<ContractDTO> getAllContracts() {
        String sql = "SELECT id , supplierNumber FROM Contracts";
        try (Statement st = DatabaseSQL.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            ArrayList<ContractDTO> list = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String supplierNumber = rs.getString("supplierNumber");
                list.add(new ContractDTO(id , supplierNumber));
            }
            return list;
        } catch (SQLException e) {
            return null ;
        }
    }

    @Override
    public ArrayList<ContractDTO> getContractsBySupplier(String supplierNumber) {
        String sql = "SELECT id , supplierNumber FROM Contracts WHERE supplierNumber = ?";
        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)) {
            ps.setString(1, supplierNumber);
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<ContractDTO> list = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String s = rs.getString("supplierNumber");
                    list.add(new ContractDTO(id , s));
                }
                return list;
            }
        } catch (SQLException e) {
            return null ;
        }
    }
}
