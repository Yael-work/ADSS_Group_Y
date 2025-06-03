package Domain.suppliers;

import DTO.Suppliers.ContractDTO;
import DTO.Suppliers.ContractTermDTO;
import Models.DBModels.suppliers.Contract;
import Models.DBModels.suppliers.ContractTerm;

import java.util.ArrayList;

public interface IContractRepository {
    Contract addContract(String supplierNumber) ;
    Contract getContract(int contractId) ;
    ArrayList<Contract> getAllContracts() ;
    Contract getContractContainsItem(String itemCode , String supplierNumber) ;
    ArrayList<Contract> getContractsBySupplier(String supplierNumber) ;
    ArrayList<Contract> getAllContractsContainingItem(String itemCode) ;
    ArrayList<ContractTerm> getAllContractTerms(int contractId) ;
    boolean addContractTerm(ContractTermDTO contractTerm);
    boolean deleteContractTerm(int contractId , String itemCode);
    boolean updateContractTerm(ContractTermDTO contractTerm);
}
