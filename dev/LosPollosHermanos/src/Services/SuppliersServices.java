package Services;

import Auxiliary.Enums.Models.Suppliers.DeliveryDays;
import DB.implementations.Suppliers.MockSupplierController;
import DB.interfaces.Suppliers.ISupplierController;
import DTO.Suppliers.ContactPersonDTO;
import DTO.Suppliers.SupplierDTO;
import Models.DBModels.suppliers.*;

import java.util.ArrayList;

public class SuppliersServices {
    private static final ISupplierController supplierController = MockSupplierController.getInstance() ;


    public static boolean addSupplier(SupplierDTO supplierDTO) {
        if(!verifySupplierDTO(supplierDTO))
            return false ;
        if(supplierController.getSupplierByNumber(supplierDTO.getNumber()) != null)
            return false ;
        return supplierController.addSupplier(supplierDTO);
    }

    private static boolean verifySupplierDTO(SupplierDTO s) {
        if(s == null)
            return false ;
        if(s.getName() == null || s.getAddress() == null || s.getPhone() == null || s.getNumber() == null || s.getPaymentTerms() == null || s.getDeliveryDays() == null)
            return false ;
        return true ;
    }

    public static SupplierDTO getSupplierByNumber(String number) {
        SupplierDTO sup = supplierController.getSupplierByNumber(number);
        if(sup == null)
            return null ;
        return sup ;
    }

    public static ArrayList<SupplierDTO> getAllSuppliersByName(String name) {
        if (name == null || name.isEmpty())
            return null ;
        return supplierController.getAllSuppliersByName(name) ;
    }

    public static boolean deleteSupplier(String number) {
        return supplierController.deleteSupplier(number) ;
    }

    public static boolean updateSupplier(SupplierDTO supplier){
        if(!verifySupplierDTO(supplier))
            return false ;
        return supplierController.updateSupplier(supplier) ;
    }

    public static boolean addContactPeople(ArrayList<ContactPersonDTO> contactPersonArrayList){
        if(contactPersonArrayList == null)
            return false ;
        if(contactPersonArrayList.isEmpty())
            return true ;
        for(ContactPersonDTO contactPersonDTO : contactPersonArrayList) {
            if (!supplierController.addContactPerson(contactPersonDTO))
                return false ;
        }
        return true ;
    }

    public static boolean removeContactPeople(ArrayList<ContactPersonDTO> contactPersonArrayList){
        if(contactPersonArrayList == null)
            return false ;
        for(ContactPersonDTO contactPersonDTO : contactPersonArrayList) {
            if(!supplierController.deleteContactPerson(contactPersonDTO))
                return false ;
        }
        return true ;
    }

    public static ArrayList<ContactPersonDTO> getContacts(String suppNumber){
        if (suppNumber == null || suppNumber.equalsIgnoreCase(""))
            return null ;
        return supplierController.getContacts(suppNumber) ;
    }

    public static boolean doesExist(ContactPersonDTO contactPersonDTO){
        return supplierController.doesExist(contactPersonDTO) ;
    }

    public static ArrayList<SupplierDTO> getAllSuppliers() {
        return supplierController.getAllSuppliers() ;
    }

    public static int getNumberOfSuppliersInDatabase(){
        return supplierController.getNumberOfSuppliersInDatabase() ;
    }
}
