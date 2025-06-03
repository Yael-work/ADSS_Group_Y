package Domain.suppliers;

import DB.DAO.Suppliers.ContractDAO;
import DB.DAO.Suppliers.ContractDAO_SQL;
import DB.DAO.Suppliers.ContractTermDAO;
import DB.DAO.Suppliers.ContractTermDAO_SQL;
import DTO.Suppliers.ContractDTO;
import DTO.Suppliers.ContractTermDTO;
import Models.DBModels.suppliers.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ContractRepository implements IContractRepository {
    private static ContractRepository instance;
    private static ContractDAO contractDAO;
    private static ContractTermDAO termsDAO ;
    private HashMap<Integer , Contract> contracts;
//    private HashMap<Integer , ArrayList<ContractTerm>> termsMap;


    private ContractRepository() {
        contracts = new HashMap<>();
//        termsMap = new HashMap<>();
        contractDAO = new ContractDAO_SQL() ;
        termsDAO = new ContractTermDAO_SQL() ;
    }

    public static ContractRepository getInstance() {
        if (instance == null) {
            instance = new ContractRepository();
        }
        return instance;
    }

    @Override
    public Contract addContract(String supplierNumber) {
        Supplier s = null ;
        ContractDTO contractDTO = contractDAO.addNewContract(supplierNumber) ;
        if(contractDTO == null)
            return null ;
        Contract c = new Contract(s) ;
        c.setId(contractDTO.getId()) ;
        c.setSupplierNumber(contractDTO.getSupplierNumber()) ;
        contracts.put(c.getId(), c) ;
        return c;
    }

    @Override
    public Contract getContract(int contractId) {
        if(contracts.containsKey(contractId)){
            return contracts.get(contractId) ;
        }
        ContractDTO contractDTO = contractDAO.getContract(contractId) ;
        if(contractDTO == null)
            return null ;
        Supplier s = null ;
        Contract c = new Contract(s) ;
        c.setSupplierNumber(contractDTO.getSupplierNumber());
        c.setId(contractDTO.getId());
        contracts.put(c.getId(), c) ;
        return c;
    }

    @Override
    public ArrayList<Contract> getAllContracts() {
        ArrayList<ContractDTO> arr = contractDAO.getAllContracts() ;
        if(arr == null)
            return null ;
        return extractContracts(arr) ;
    }

    @Override
    public Contract getContractContainsItem(String itemCode, String supplierNumber) {
        ArrayList<ContractDTO> arr = contractDAO.getContractsBySupplier(supplierNumber) ;
        if(arr == null)
            return null ;
        for(ContractDTO con : arr) {
            Contract c ;
            if (contracts.containsKey(con.getId())) {
                c = contracts.get(con.getId());
            }else {
                Supplier s = null;
                c = new Contract(s);
                c.setSupplierNumber(con.getSupplierNumber());
                c.setId(con.getId());
                contracts.put(c.getId(), c);
            }
            if(c.doesContainItem(itemCode))
                return c ;
        }
        return null;
    }

    @Override
    public ArrayList<ContractTerm> getAllContractTerms(int contractId){
//        if(termsMap.containsKey(contractId))
//            return termsMap.get(contractId) ;
        ArrayList<ContractTermDTO> arr = termsDAO.getAllContractTerm(contractId) ;
        if(arr == null)
            return null ;
        ArrayList<ContractTerm> result = new ArrayList<>();
        for(ContractTermDTO con : arr) {
            Item item = new Item(con.getItemDTO().getCode() , con.getItemDTO().getName()) ;
            AmountSpecifier amountSpecifier = new AmountSpecifier(con.getAmountSpecifierDTO().getMinimalAmount() , con.getAmountSpecifierDTO().getBaseDiscount()) ;
            result.add(new ContractTerm(item , con.getPrice() , amountSpecifier)) ;
        }
//        termsMap.put(contractId, result) ;
        return result;
    }

    @Override
    public ArrayList<Contract> getContractsBySupplier(String supplierNumber) {
        ArrayList<ContractDTO> arr = contractDAO.getContractsBySupplier(supplierNumber) ;
        if(arr == null)
            return null ;
        return extractContracts(arr) ;
    }

    @Override
    public ArrayList<Contract> getAllContractsContainingItem(String itemCode) {
        ArrayList<ContractTermDTO> arr = termsDAO.getAllContractTermContainingItem(itemCode) ;
        if(arr == null)
            return null ;
        HashMap<Integer , Contract> map = new HashMap<>();
        for(ContractTermDTO con : arr) {
            if(!map.containsKey(con.getContractID())){
                Contract contract = getContract(con.getContractID()) ;
                if(contract == null)
                    return null ;
                map.put(contract.getId() , contract) ;
            }
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public boolean addContractTerm(ContractTermDTO contractTerm){
        boolean r = termsDAO.addContractTerm(contractTerm) ;
        if(!r)
            return false ;
//        if(!termsMap.containsKey(contractTerm.getContractID()))
//            termsMap.put(contractTerm.getContractID() , new ArrayList<>());
//        Item item = new Item(contractTerm.getItemDTO().getCode() , contractTerm.getItemDTO().getName()) ;
//        AmountSpecifier amountSpecifier = new AmountSpecifier(contractTerm.getAmountSpecifierDTO().getMinimalAmount() , contractTerm.getAmountSpecifierDTO().getBaseDiscount()) ;
//        termsMap.get(contractTerm.getContractID()).add(new ContractTerm(new ContractTerm(item , contractTerm.getPrice() , amountSpecifier))) ;
        return r ;
    }

    @Override
    public boolean deleteContractTerm(int contractId , String itemCode){
        boolean r = termsDAO.deleteContractTerm(contractId, itemCode) ;
        if(!r)
            return false ;
//        if(termsMap.containsKey(contractId)) {
//            ArrayList<ContractTerm> toCheck = termsMap.get(contractId) ;
//            for(int i = 0 ; i < toCheck.size() ; i++) {
//                if(toCheck.get(i).getItem().getCode().equals(itemCode)){
//                    toCheck.remove(i) ;
//                    break;
//                }
//            }
//        }
        return r ;
    }

    @Override
    public boolean updateContractTerm(ContractTermDTO contractTerm){
        boolean r = termsDAO.updateContractTerm(contractTerm) ;
        if(!r)
            return false ;
//        Item item = new Item(contractTerm.getItemDTO().getCode() , contractTerm.getItemDTO().getName()) ;
//        AmountSpecifier amountSpecifier = new AmountSpecifier(contractTerm.getAmountSpecifierDTO().getMinimalAmount() , contractTerm.getAmountSpecifierDTO().getBaseDiscount()) ;
//        ContractTerm c = new ContractTerm(new ContractTerm(item , contractTerm.getPrice() , amountSpecifier)) ;
//        if(!termsMap.containsKey(contractTerm.getContractID())){
//            ArrayList<ContractTerm> arr = new ArrayList<>();
//            arr.add(c) ;
//            termsMap.put(contractTerm.getContractID() , arr);
//        }else{
//            ArrayList<ContractTerm> arr = termsMap.get(contractTerm.getContractID()) ;
//            for(int i = 0 ; i < arr.size() ; i++) {
//                if(arr.get(i).getItem().getCode().equals(contractTerm.getItemDTO().getCode())){
//                    arr.set(i , c) ;
//                    break;
//                }
//            }
//        }
        return r ;
    }


    private ArrayList<Contract> extractContracts(ArrayList<ContractDTO> arr) {
        ArrayList<Contract> result = new ArrayList<>();
        for(ContractDTO contract : arr){
            if(contracts.containsKey(contract.getId())){
                result.add(contracts.get(contract.getId())) ;
            }else{
                Supplier s = null ;
                Contract con = new Contract(s) ;
                con.setId(contract.getId()) ;
                con.setSupplierNumber(contract.getSupplierNumber());
                result.add(con) ;
                contracts.put(con.getId(), con) ;
            }
        }
        return result;
    }
}
