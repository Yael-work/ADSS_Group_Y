package DB.DAO.Suppliers;

import DTO.Suppliers.SupplierDTO;
import java.util.ArrayList;

public interface SupplierDAO {
    boolean addSupplier(SupplierDTO supplier);
    SupplierDTO getSupplier(String number);
    boolean deleteSupplier(String number);
    boolean updateSupplier(SupplierDTO supplier);
    ArrayList<SupplierDTO> getAllSuppliers() ;
    ArrayList<SupplierDTO> getAllSuppliersByName(String name) ;
}
