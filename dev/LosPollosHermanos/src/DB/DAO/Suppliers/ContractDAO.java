package DB.DAO.Suppliers;

import DTO.Suppliers.ContractDTO;

import java.util.ArrayList;

public interface ContractDAO {
    ContractDTO addNewContract(String supplierNumber) ;
    ContractDTO getContract(int contractId);
    ArrayList<ContractDTO> getAllContracts();
    ArrayList<ContractDTO> getContractsBySupplier(String supplierNumber);
}
