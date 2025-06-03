package DB.DAO.Suppliers;

import Auxiliary.EnumConvertor;
import DB.base.DatabaseSQL;
import DTO.Suppliers.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ContractTermDAO_SQL implements ContractTermDAO {
    @Override
    public boolean addContractTerm(ContractTermDTO contractTerm) {
        String sql = "INSERT INTO ContractTerms(itemCode , itemName , minimalAmount , baseDiscount , price , contractId) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement ps = DatabaseSQL.getConnection()
                .prepareStatement(sql)) {
            ps.setString(1 , contractTerm.getItemDTO().getCode());
            ps.setString(2, contractTerm.getItemDTO().getName());
            ps.setInt(3, contractTerm.getAmountSpecifierDTO().getMinimalAmount());
            ps.setFloat(4, (float) contractTerm.getAmountSpecifierDTO().getBaseDiscount());
            ps.setFloat(5, (float) contractTerm.getPrice());
            ps.setInt(6, contractTerm.getContractID());
            ps.executeUpdate();
            return true ;
        } catch (SQLException e) {
            return false ;
        }
    }

    @Override
    public ContractTermDTO getContractTerm(int contractId, String itemCode) {
        String sql = "SELECT itemCode , itemName , minimalAmount , baseDiscount , price , contractId FROM ContractTerms WHERE contractId = ? AND ItemCode = ?";
        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)) {
            ps.setInt(1, contractId);
            ps.setString(2, itemCode);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    String itemC = rs.getString("itemCode");
                    String itemName = rs.getString("itemName");
                    int minimalAmount = rs.getInt("minimalAmount");
                    double baseDiscount = (double) rs.getFloat("baseDiscount");
                    double price = (double) rs.getFloat("price");
                    int id = rs.getInt("contractId");
                    ItemDTO item = new ItemDTO(itemC , itemName) ;
                    AmountSpecifierDTO amountSpecifierDTO = new AmountSpecifierDTO(minimalAmount , baseDiscount) ;
                    return new ContractTermDTO(item , amountSpecifierDTO , price , id) ;
                }
                return null ;
            }
        } catch (SQLException e) {
            return null ;
        }
    }

    @Override
    public boolean deleteContractTerm(int contractId, String itemCode) {
        String sql = "DELETE FROM ContractTerms WHERE contractId = ? AND ItemCode = ?";

        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)){

            ps.setInt(1, contractId);
            ps.setString(2, itemCode);
            ps.executeUpdate();
            return true ;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateContractTerm(ContractTermDTO contractTerm) {
        String sql = "UPDATE ContractTerms SET itemCode = ? , itemName = ?, minimalAmount = ?, baseDiscount = ?, price = ? , contractId = ? WHERE contractId = ? AND ItemCode = ?";
        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)) {
            ps.setString(1, contractTerm.getItemDTO().getCode());
            ps.setString(2, contractTerm.getItemDTO().getName());
            ps.setInt(3, contractTerm.getAmountSpecifierDTO().getMinimalAmount());
            ps.setFloat(4, (float) contractTerm.getAmountSpecifierDTO().getBaseDiscount());
            ps.setFloat(5, (float) contractTerm.getPrice());
            ps.setInt(6, contractTerm.getContractID());

            ps.setInt(7, contractTerm.getContractID());
            ps.setString(8 , contractTerm.getItemDTO().getCode());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false ;
        }
    }

    @Override
    public ArrayList<ContractTermDTO> getAllContractTerm(int contractId) {
        String sql = "SELECT * FROM ContractTerms WHERE contractId = ?";
        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)){
            ps.setInt(1 , contractId);
            try(ResultSet rs = ps.executeQuery()) {
                ArrayList<ContractTermDTO> list = new ArrayList<>();
                while (rs.next()) {
                    String itemC = rs.getString("itemCode");
                    String itemName = rs.getString("itemName");
                    int minimalAmount = rs.getInt("minimalAmount");
                    double baseDiscount = (double) rs.getFloat("baseDiscount");
                    double price = (double) rs.getFloat("price");
                    int id = rs.getInt("contractId");
                    ItemDTO item = new ItemDTO(itemC, itemName);
                    AmountSpecifierDTO amountSpecifierDTO = new AmountSpecifierDTO(minimalAmount, baseDiscount);
                    list.add(new ContractTermDTO(item, amountSpecifierDTO, price, id));
                }
                return list;
            }
        } catch (SQLException e) {
            return null ;
        }
    }

    @Override
    public ArrayList<ContractTermDTO> getAllContractTermContainingItem(String itemCode) {
        String sql = "SELECT itemCode , itemName , minimalAmount , baseDiscount , price , contractId FROM ContractTerms WHERE itemCode = ?";
        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)){
            ps.setString(1 , itemCode);
            try(ResultSet rs = ps.executeQuery()) {
                ArrayList<ContractTermDTO> list = new ArrayList<>();
                while (rs.next()) {
                    String itemC = rs.getString("itemCode");
                    String itemName = rs.getString("itemName");
                    int minimalAmount = rs.getInt("minimalAmount");
                    double baseDiscount = (double) rs.getFloat("baseDiscount");
                    double price = (double) rs.getFloat("price");
                    int id = rs.getInt("contractId");
                    ItemDTO item = new ItemDTO(itemC, itemName);
                    AmountSpecifierDTO amountSpecifierDTO = new AmountSpecifierDTO(minimalAmount, baseDiscount);
                    list.add(new ContractTermDTO(item, amountSpecifierDTO, price, id));
                }
                return list;
            }
        } catch (SQLException e) {
            return null ;
        }
    }
}
