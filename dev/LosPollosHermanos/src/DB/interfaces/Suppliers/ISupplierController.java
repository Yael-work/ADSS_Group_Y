package DB.interfaces.Suppliers;

import Auxiliary.Enums.Models.Suppliers.DeliveryDays;
import DTO.Suppliers.ContactPersonDTO;
import DTO.Suppliers.SupplierDTO;
import java.util.ArrayList;

public interface ISupplierController{
    boolean addSupplier(SupplierDTO supplierDTO);
    SupplierDTO getSupplierByNumber(String number);
    ArrayList<SupplierDTO> getAllSuppliersByName(String name) ;
    ArrayList<SupplierDTO> getAllSuppliers() ;
    boolean updateSupplier(SupplierDTO supplier);
    boolean deleteSupplier(String supplierNumber) ;
    boolean addContactPerson(ContactPersonDTO contactPerson);
    boolean deleteContactPerson(ContactPersonDTO contactPerson);
    ArrayList<ContactPersonDTO> getContacts(String supNumber) ;
    boolean doesExist(ContactPersonDTO contactPerson);
    int getNumberOfSuppliersInDatabase() ;
}
