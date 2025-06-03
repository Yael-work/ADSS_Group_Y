package DB.interfaces.Suppliers;

import DTO.Suppliers.*;

import java.util.ArrayList;

public interface IContractController {
    int getNumberOfContractsInDatabase() ;
    ContractDTO addContract(String supplierNumber);
    boolean addTerm(ContractTermDTO contractTermDTO);
    ArrayList<ContractDTO> getAllContracts() ;
    ArrayList<ContractTermDTO> getContractTerms(int contractId) ;
    int getContractIDContainsItem(String itemCode , String supplierNumber);
    boolean updateContractTerm(int contractId , String itemCode , AmountSpecifierDTO amountSpecifierDTO) ;
    ContractDTO getContractById(int contractId) ;
    ArrayList<ContractDTO> getContractsBySupplier(String supplierNumber) ;
    ArrayList<ContractDTO> getAllContractsContainingItem(String itemCode);
    double getPriceOfItem(int contractId , String itemCode , int quantity) ;
    SupplierDTO getCheapestSupplierForProduct(String itemCode , int quantity) ;
}
