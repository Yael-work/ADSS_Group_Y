package Domain.suppliers;

import Auxiliary.Enums.Models.Suppliers.DeliveryDays;
import DB.DAO.Suppliers.ContactPersonDAO;
import DB.DAO.Suppliers.ContactPersonDAO_SQL;
import DB.DAO.Suppliers.SupplierDAO;
import DB.DAO.Suppliers.SupplierDAO_SQL;
import DTO.Suppliers.ContactPersonDTO;
import DTO.Suppliers.SupplierDTO;
import Models.DBModels.suppliers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SuppliersRepository implements ISuppliersRepository {
    HashMap<String, Supplier> suppliersMap;
    HashMap<String, ArrayList<ContactPerson>> contactMap;
    SupplierDAO supplierDAO;
    ContactPersonDAO contactPersonDAO;
    private static SuppliersRepository instance = null;

    private SuppliersRepository() {
        suppliersMap = new HashMap<>();
        contactMap = new HashMap<>();
        supplierDAO = new SupplierDAO_SQL() ;
        contactPersonDAO = new ContactPersonDAO_SQL() ;
    }
    public static SuppliersRepository getInstance(){
        if(instance == null){
            instance = new SuppliersRepository();
        }
        return instance;
    }

    @Override
    public boolean addSupplier(SupplierDTO supplier) {
        if(suppliersMap.containsKey(supplier.getNumber()))
            return false ;
        boolean r = supplierDAO.addSupplier(supplier);
        if(!r)
            return false ;
        Supplier s = new Supplier(supplier) ;
        suppliersMap.put(s.getNumber(), s) ;
        return r ;
    }

    @Override
    public Supplier getSupplier(String supNumber) {
        if(suppliersMap.containsKey(supNumber))
            return suppliersMap.get(supNumber) ;
        SupplierDTO supplierDTO = supplierDAO.getSupplier(supNumber);
        if(supplierDTO == null)
            return null ;
        Supplier s = new Supplier(supplierDTO);
        suppliersMap.put(supNumber, s);
        return s;
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplier) {
        boolean r = supplierDAO.updateSupplier(supplier);
        if(!r)
            return false ;
        if(suppliersMap.containsKey(supplier.getNumber()))
            suppliersMap.get(supplier.getNumber()).update(supplier);
        return r;
    }

    @Override
    public boolean deleteSupplier(String supplierNumber) {
        boolean r = supplierDAO.deleteSupplier(supplierNumber);
        if(!r)
            return false ;
        suppliersMap.remove(supplierNumber) ; //doesn't care if the key exists
        deleteContacts(supplierNumber) ;
        return r;
    }

    @Override
    public ArrayList<Supplier> getAllSuppliers() {
        ArrayList<SupplierDTO> arr = supplierDAO.getAllSuppliers() ;
        ArrayList<Supplier> suppliers = new ArrayList<>();
        for(SupplierDTO d : arr){
            suppliers.add(new Supplier(d));
        }
        return suppliers ;
    }

    @Override
    public ArrayList<Supplier> getAllSuppliersByName(String name) {
        ArrayList<SupplierDTO> arr = supplierDAO.getAllSuppliersByName(name) ;
        ArrayList<Supplier> suppliers = new ArrayList<>();
        for(SupplierDTO d : arr){
            suppliers.add(new Supplier(d));
        }
        return suppliers ;
    }




    @Override
    public boolean addContactPerson(ContactPersonDTO contactPerson) {
        boolean r = contactPersonDAO.addContactPerson(contactPerson);
        if(!r)
            return false ;
        if(!contactMap.containsKey(contactPerson.getSuppNumber())){
            getContacts(contactPerson.getSuppNumber()) ;
        }
        return r ;
    }

    @Override
    public boolean deleteContactPerson(ContactPersonDTO contactPerson) {
        boolean r = contactPersonDAO.deleteContactPerson(contactPerson);
        if(!r)
            return false ;
        if(contactMap.containsKey(contactPerson.getSuppNumber())){
            ArrayList<ContactPerson> contactPersons = contactMap.get(contactPerson.getSuppNumber());
            for(int i = 0 ; i < contactPersons.size(); i++){
                if(Objects.equals(contactPerson.getName(), contactPersons.get(i).getName()) && Objects.equals(contactPerson.getPhone(), contactPersons.get(i).getPhone())){
                    contactPersons.remove(i);
                    break;
                }
            }
        }
        return r;
    }

    @Override
    public ArrayList<ContactPerson> getContacts(String supNumber) {
        if(contactMap.containsKey(supNumber))
            return contactMap.get(supNumber) ;
        ArrayList<ContactPersonDTO> arr = contactPersonDAO.getContacts(supNumber);
        if(arr == null)
            return null ;
        ArrayList<ContactPerson> contacts = new ArrayList<>();
        for(ContactPersonDTO c : arr)
            contacts.add(new ContactPerson(c.getName() , c.getPhone())) ;
        contactMap.put(supNumber, contacts) ;
        return contacts;
    }

    @Override
    public boolean doesExist(ContactPersonDTO contactPerson) {
        ArrayList<ContactPersonDTO> arr = contactPersonDAO.getContacts(contactPerson.getSuppNumber());
        if(arr == null)
            return false ;
        for(ContactPersonDTO c : arr){
            if(c.equals(contactPerson))
                return true;
        }
        return false;
    }

    @Override
    public boolean deleteContacts(String supNumber) {
        boolean r = contactPersonDAO.deleteContacts(supNumber);
        if(!r)
            return false ;
        if(contactMap.containsKey(supNumber)){
            contactMap.put(supNumber, new ArrayList<>()) ;
        }
        return r;
    }
}
