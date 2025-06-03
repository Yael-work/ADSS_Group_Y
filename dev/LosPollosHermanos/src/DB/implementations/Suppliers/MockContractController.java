package DB.implementations.Suppliers;

import DB.interfaces.Suppliers.IContractController;
import DTO.Suppliers.*;
import Domain.suppliers.ContractRepository;
import Domain.suppliers.IContractRepository;
import Domain.suppliers.ISuppliersRepository;
import Domain.suppliers.SuppliersRepository;
import Models.DBModels.suppliers.AmountSpecifier;
import Models.DBModels.suppliers.Contract;
import Models.DBModels.suppliers.ContractTerm;
import Models.DBModels.suppliers.Item;

import java.util.ArrayList;

public class MockContractController implements IContractController {
    private static MockContractController instance;
    private static IContractRepository contractRepository;

    private MockContractController() {
        contractRepository = ContractRepository.getInstance();
    }

    public static MockContractController getInstance() {
        if (instance == null) {
            instance = new MockContractController();
        }
        return instance;
    }


    @Override
    public int getNumberOfContractsInDatabase() {
        return getAllContracts().size();
    }

    @Override
    public ContractDTO addContract(String supplierNumber) {
        Contract contract = contractRepository.addContract(supplierNumber);
        if(contract == null)
            return null;
        return new ContractDTO(contract.getId() , supplierNumber) ;
    }

    @Override
    public boolean addTerm(ContractTermDTO contractTermDTO) {
        Contract contract = contractRepository.getContract(contractTermDTO.getContractID()) ;
        if(contract == null)
            return false;
        Item item = new Item(contractTermDTO.getItemDTO().getCode() , contractTermDTO.getItemDTO().getName()) ;
        AmountSpecifier amountSpecifier = new AmountSpecifier(contractTermDTO.getAmountSpecifierDTO().getMinimalAmount() ,
                contractTermDTO.getAmountSpecifierDTO().getBaseDiscount()) ;

        ContractTerm contractTerm = new ContractTerm(item , contractTermDTO.getPrice() , amountSpecifier) ;
        return contract.addTerm(contractTerm);
    }

    @Override
    public ArrayList<ContractDTO> getAllContracts() {
        ArrayList<Contract> arr = contractRepository.getAllContracts();
        if(arr == null)
            return null;
        ArrayList<ContractDTO> result = new ArrayList<>();
        for (Contract contract : arr) {
            result.add(new ContractDTO(contract.getId() , contract.getSupplier().getNumber()));
        }
        return result;
    }

    @Override
    public ArrayList<ContractTermDTO> getContractTerms(int contractId) {
        Contract contract = contractRepository.getContract(contractId) ;
        ArrayList<ContractTerm> arr = contract.getContractTerms();
        if(arr == null)
            return null;
        ArrayList<ContractTermDTO> res = new ArrayList<>();
        for (ContractTerm c : arr) {
            ItemDTO itemDTO = c.getItem().toDTO() ;
            AmountSpecifierDTO amountSpecifierDTO = c.getAmountSpecifier().toDTO() ;
            res.add(new ContractTermDTO(itemDTO , amountSpecifierDTO , c.getPrice() , contractId)) ;
        }
        return res;
    }

    @Override
    public int getContractIDContainsItem(String itemCode, String supplierNumber) {
        Contract contract = contractRepository.getContractContainsItem(itemCode , supplierNumber);
        if(contract == null)
            return -1 ;
        return contract.getId() ;
    }

    @Override
    public boolean updateContractTerm(int contractId, String itemCode, AmountSpecifierDTO amountSpecifierDTO) {
        Contract contract = contractRepository.getContract(contractId) ;
        if(contract == null)
            return false ;
        AmountSpecifier amountSpecifier = new AmountSpecifier(amountSpecifierDTO.getMinimalAmount() , amountSpecifierDTO.getBaseDiscount()) ;
        return contract.updateTerm(itemCode , amountSpecifier) ;
    }

    @Override
    public ContractDTO getContractById(int contractId) {
        Contract contract = contractRepository.getContract(contractId) ;
        return new ContractDTO(contract.getId() , contract.getSupplier().getNumber());
    }

    @Override
    public ArrayList<ContractDTO> getContractsBySupplier(String supplierNumber) {
        ArrayList<Contract> arr = contractRepository.getContractsBySupplier(supplierNumber) ;
        if(arr == null)
            return null ;
        ArrayList<ContractDTO> res = new ArrayList<>();
        for (Contract contract : arr) {
            res.add(new ContractDTO(contract.getId() , contract.getSupplier().getNumber()));
        }
        return res;
    }

    @Override
    public ArrayList<ContractDTO> getAllContractsContainingItem(String itemCode) {
        ArrayList<Contract> arr = contractRepository.getAllContractsContainingItem(itemCode) ;
        if(arr == null)
            return null ;
        ArrayList<ContractDTO> res = new ArrayList<>();
        for (Contract contract : arr) {
            res.add(new ContractDTO(contract.getId() , contract.getSupplier().getNumber()));
        }
        return res;
    }

    @Override
    public double getPriceOfItem(int contractId , String itemCode , int quantity){
        Contract contract = contractRepository.getContract(contractId) ;
        return contract.getTotalPriceOfItems(itemCode , quantity) ;
    }

    @Override
    public SupplierDTO getCheapestSupplierForProduct(String itemCode , int quantity) {
        ArrayList<Contract> arr = contractRepository.getAllContractsContainingItem(itemCode) ;
        if(arr == null || arr.isEmpty())
            return null;
        Contract min = arr.get(0) ;
        double minTotalPrice = min.getTotalPriceOfItems(itemCode , quantity) ;
        for(Contract contract : arr){
            double curr = contract.getTotalPriceOfItems(itemCode , quantity) ;
            if(curr < minTotalPrice){
                minTotalPrice = curr ;
                min = contract ;
            }
        }
        return min.getSupplier().toDTO() ;
    }
}
