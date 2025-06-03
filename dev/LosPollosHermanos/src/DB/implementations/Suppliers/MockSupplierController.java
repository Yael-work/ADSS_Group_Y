package DB.implementations.Suppliers;

import Auxiliary.Enums.Models.Suppliers.DeliveryDays;
import Auxiliary.SupplierHelper;
import DB.interfaces.Suppliers.ISupplierController;
import DTO.Suppliers.ContactPersonDTO;
import DTO.Suppliers.SupplierDTO;
import Domain.suppliers.ISuppliersRepository;
import Domain.suppliers.SuppliersRepository;
import Models.DBModels.suppliers.ContactPerson;
import Models.DBModels.suppliers.PaymentTerms;
import Models.DBModels.suppliers.Supplier;

import java.util.ArrayList;
import java.util.List;

public class MockSupplierController implements ISupplierController {
    private static MockSupplierController instance = null;
    ISuppliersRepository suppliersRepository = null;

    private MockSupplierController() {
        suppliersRepository = SuppliersRepository.getInstance();
    }
    public static MockSupplierController getInstance() {
        if (instance == null) {
            instance = new MockSupplierController();
        }
        return instance;
    }

    @Override
    public boolean addSupplier(SupplierDTO supplierDTO) {
        return suppliersRepository.addSupplier(supplierDTO);
    }

    @Override
    public SupplierDTO getSupplierByNumber(String number) {
        Supplier s = suppliersRepository.getSupplier(number);
        if(s == null)
            return null;
        return s.toDTO() ;
    }

    @Override
    public ArrayList<SupplierDTO> getAllSuppliersByName(String name){
        ArrayList<Supplier> suppliers = suppliersRepository.getAllSuppliersByName(name) ;
        ArrayList<SupplierDTO> supplierDTOs = new ArrayList<>();
        for (Supplier s : suppliers) {
            supplierDTOs.add(s.toDTO());
        }
        return supplierDTOs;
    }

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() {
        ArrayList<SupplierDTO> arr = new ArrayList<>();
        ArrayList<Supplier> suppliers = suppliersRepository.getAllSuppliers();
        if(suppliers == null)
            return arr;
        for (Supplier s : suppliers) {
            arr.add(s.toDTO());
        }
        return arr;
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplier) {
        return suppliersRepository.updateSupplier(supplier);
    }

    @Override
    public boolean deleteSupplier(String supplierNumber) {
        return suppliersRepository.deleteSupplier(supplierNumber);
    }

    @Override
    public boolean addContactPerson(ContactPersonDTO contactPerson) {
        return suppliersRepository.addContactPerson(contactPerson);
    }

    @Override
    public boolean deleteContactPerson(ContactPersonDTO contactPerson) {
        return suppliersRepository.deleteContactPerson(contactPerson);
    }

    @Override
    public ArrayList<ContactPersonDTO> getContacts(String supNumber) {
        ArrayList<ContactPersonDTO> arr = new ArrayList<>();
        ArrayList<ContactPerson> contacts = suppliersRepository.getContacts(supNumber);
        if(contacts == null)
            return arr;
        for (ContactPerson contactPerson : contacts) {
            ContactPersonDTO x = contactPerson.toDTO() ;
            x.setSuppNumber(supNumber);
            arr.add(x);
        }
        return arr;
    }

    @Override
    public boolean doesExist(ContactPersonDTO contactPerson) {
        return suppliersRepository.doesExist(contactPerson);
    }

    @Override
    public int getNumberOfSuppliersInDatabase(){
        ArrayList<Supplier> arr =  suppliersRepository.getAllSuppliers();
        if(arr == null)
            return 0;
        return arr.size();
    }
}
