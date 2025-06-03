package DB.DAO.Suppliers;

import DTO.Suppliers.ContactPersonDTO;
import Models.DBModels.suppliers.ContactPerson;

import java.util.ArrayList;

public interface ContactPersonDAO {
    boolean addContactPerson(ContactPersonDTO contactPerson);
    boolean deleteContactPerson(ContactPersonDTO contactPerson);
    ArrayList<ContactPersonDTO> getContacts(String supNumber);
    boolean deleteContacts(String supNumber) ;
}
