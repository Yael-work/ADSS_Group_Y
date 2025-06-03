package Services;

import DB.implementations.Suppliers.MockContractController;
import DB.interfaces.Suppliers.IContractController;
import DTO.Suppliers.AmountSpecifierDTO;
import DTO.Suppliers.ContractDTO;
import DTO.Suppliers.ContractTermDTO;
import DTO.Suppliers.ItemDTO;

import java.util.ArrayList;

public class ContractsServices {
    private static final IContractController contractController = MockContractController.getInstance();


    public static int getNumberOfContractsInDatabase(){
        return contractController.getNumberOfContractsInDatabase() ;
    }

    public static boolean doesSupplierSupplyItem(String supplierNumber , ItemDTO itemDTO){
        if(supplierNumber == null || itemDTO == null)
            return false;
        return contractController.getContractIDContainsItem(itemDTO.getCode() , supplierNumber) > 0;
    }

    public static int addContract(String supplierNumber , ArrayList<ContractTermDTO> terms){
        if(terms == null || supplierNumber == null || terms.isEmpty())
            return -1;
        ContractDTO contract = contractController.addContract(supplierNumber) ;
        if(contract.getId() == -1)
            return -1;
        for(ContractTermDTO term : terms){
            term.setContractID(contract.getId()) ;
            if(!contractController.addTerm(term))
                throw new RuntimeException("sql problem") ;
        }
        return contract.getId() ;
    }

    public static ArrayList<ContractDTO> getAllContracts(){
        return contractController.getAllContracts() ;
    }

    public static ArrayList<ContractTermDTO> getContractTerms(int contractId){
        if(contractId < 0)
            return null;
        return contractController.getContractTerms(contractId) ;
    }

    public static int getContractIDContainsItem(String itemCode , String supplierNumber){
        if(itemCode == null || supplierNumber == null)
            return -1;
        return contractController.getContractIDContainsItem(itemCode , supplierNumber) ;
    }

    public static boolean updateContractTerm(int contractId , String itemCode , AmountSpecifierDTO amountSpecifierDTO){
        if(contractId < 0 || itemCode == null || amountSpecifierDTO == null)
            return false;
        return contractController.updateContractTerm(contractId , itemCode , amountSpecifierDTO) ;
    }

    public static ContractDTO getContractById(int contractId){
        if(contractId < 0)
            return null;
        return contractController.getContractById(contractId) ;
    }

    public static ArrayList<ContractDTO> getContractsBySupplier(String supplierNumber){
        if(supplierNumber == null || supplierNumber.isEmpty())
            return null;
        return contractController.getContractsBySupplier(supplierNumber) ;
    }

    public static ArrayList<ContractDTO> getAllContractsContainingItem(String itemCode){
        if (itemCode == null || itemCode.isEmpty())
            return null;
        return contractController.getAllContractsContainingItem(itemCode) ;
    }

    public static double getPriceOfItem(int contractId , String itemCode , int quantity){
        if(contractId < 0 || itemCode == null || quantity < 0)
            return -1 ;
        return contractController.getPriceOfItem(contractId , itemCode , quantity) ;
    }
}
