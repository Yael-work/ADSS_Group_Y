package Domain.suppliers;

import Auxiliary.Enums.Models.Suppliers.DeliveryDays;
import DTO.Suppliers.ContactPersonDTO;
import DTO.Suppliers.SupplierDTO;
import Models.DBModels.suppliers.ContactPerson;
import Models.DBModels.suppliers.Supplier;

import java.util.ArrayList;

public interface ISuppliersRepository {
    boolean addSupplier(SupplierDTO supplier);
    Supplier getSupplier(String supNumber);
    ArrayList<Supplier> getAllSuppliersByName(String name) ;
    boolean updateSupplier(SupplierDTO supplier);
    boolean deleteSupplier(String supplierNumber) ;
    ArrayList<Supplier> getAllSuppliers() ;

    boolean addContactPerson(ContactPersonDTO contactPerson);
    boolean deleteContactPerson(ContactPersonDTO contactPerson);
    ArrayList<ContactPerson> getContacts(String supNumber) ;
    boolean doesExist(ContactPersonDTO contactPerson);
    boolean deleteContacts(String supNumber) ;
}
