package DB.DAO.Suppliers;

import DTO.Suppliers.ContractTermDTO;

import java.util.ArrayList;

public interface ContractTermDAO {
    boolean addContractTerm(ContractTermDTO contractTerm);
    ContractTermDTO getContractTerm(int contractId , String itemCode);
    boolean deleteContractTerm(int contractId , String itemCode);
    boolean updateContractTerm(ContractTermDTO contractTerm);
    ArrayList<ContractTermDTO> getAllContractTerm(int contractId);
    ArrayList<ContractTermDTO> getAllContractTermContainingItem(String itemCode);
}
