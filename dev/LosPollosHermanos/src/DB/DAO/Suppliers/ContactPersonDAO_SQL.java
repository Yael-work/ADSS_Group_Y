package DB.DAO.Suppliers;

import Auxiliary.EnumConvertor;
import DB.base.DatabaseSQL;
import DTO.Suppliers.ContactPersonDTO;
import DTO.Suppliers.PaymentTermsDTO;
import DTO.Suppliers.SupplierDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactPersonDAO_SQL implements ContactPersonDAO {
    @Override
    public boolean addContactPerson(ContactPersonDTO contactPerson) {
        String sql = "INSERT INTO contactPerson(sup_number , name , phone) VALUES(?,?,?)";
        try (PreparedStatement ps = DatabaseSQL.getConnection()
                .prepareStatement(sql)) {
            ps.setString(1, contactPerson.getSuppNumber());
            ps.setString(2, contactPerson.getName());
            ps.setString(3, contactPerson.getPhone());
            ps.executeUpdate();
            return true ;
        } catch (SQLException e) {
            return false ;
        }
    }

    @Override
    public boolean deleteContactPerson(ContactPersonDTO contactPerson) {
        String sql = "DELETE FROM contactPerson WHERE sup_number = ? AND name  = ? AND phone  = ?";

        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)){
            ps.setString(1, contactPerson.getSuppNumber());
            ps.setString(2, contactPerson.getName());
            ps.setString(3, contactPerson.getPhone());
            ps.executeUpdate();
            return true ;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public ArrayList<ContactPersonDTO> getContacts(String supNumber) {
        String sql = "SELECT sup_number, name, phone FROM contactPerson WHERE sup_number = ?";
        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)) {
            ps.setString(1, supNumber);
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<ContactPersonDTO> contacts = new ArrayList<>();
                while(rs.next()) {
                   contacts.add(new ContactPersonDTO(rs.getString("name"), rs.getString("phone") , rs.getString("sup_number")));
                }
                return contacts ;
            }
        } catch (SQLException e) {
            return null ;
        }
    }

    @Override
    public boolean deleteContacts(String supNumber){
        String sql = "DELETE FROM contactPerson WHERE sup_number = ?";

        try (PreparedStatement ps = DatabaseSQL.getConnection().prepareStatement(sql)){
            ps.setString(1, supNumber);
            ps.executeUpdate();
            return true ;
        } catch (SQLException e) {
            return false;
        }
    }
}
