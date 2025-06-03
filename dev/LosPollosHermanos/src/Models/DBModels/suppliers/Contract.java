package Models.DBModels.suppliers;

import DTO.Suppliers.ContractTermDTO;
import Domain.suppliers.ContractRepository;
import Domain.suppliers.IContractRepository;
import Domain.suppliers.ISuppliersRepository;
import Domain.suppliers.SuppliersRepository;

import java.util.ArrayList;

public class Contract {
    int id;
    String supplierNumber ;
    Supplier supplier;
    ArrayList<ContractTerm> contractTerms;
    private final ISuppliersRepository suppliersRepository = SuppliersRepository.getInstance();
    private final IContractRepository contractRepository = ContractRepository.getInstance();

    public Contract(Contract other) {
        this.id = other.id;
        this.supplier = other.supplier;
        this.contractTerms = new ArrayList<>();
        for(ContractTerm term : other.contractTerms){
            this.contractTerms.add(new ContractTerm(term));
        }
    }

    public Contract(Supplier supplier) {
        id = -1 ;
        this.supplier = supplier;
        contractTerms = new ArrayList<>();
    }

    public boolean doesContainItem(String itemCode) {
        getContractTerms() ;
        for (ContractTerm contractTerm : contractTerms) {
            if (itemCode.equals(contractTerm.getItem().getCode()))
                return true;
        }
        return false;
    }

    public Supplier getSupplier() {
        if(supplier == null){
            if(supplierNumber == null)
                throw new RuntimeException("Supplier number is null");
            supplier = suppliersRepository.getSupplier(supplierNumber);
            if(supplier == null)
                throw new RuntimeException("sql problem or supplier not found");
        }
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getSupplierNumber() {
        if(supplierNumber == null)
            supplierNumber = supplier.getNumber();
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public ArrayList<ContractTerm> getContractTerms() {
        this.contractTerms = contractRepository.getAllContractTerms(id) ;
        if(contractTerms == null)
            throw new RuntimeException("sql problem");
        return contractTerms;
    }

    public void setContractTerms(ArrayList<ContractTerm> contractTerms) {
        this.contractTerms = contractTerms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Supplier : " + supplier + " , terms : " + contractTerms.size();
    }

    public String expandedToString(){
        String result = "Supplier : " + supplier + "\n";
        for (ContractTerm contractTerm : contractTerms) {
            result += contractTerm.toString() + "\n";
        }
        return result;
    }

    public double getPriceOfItem(String itemCode) {
        for (ContractTerm contractTerm : contractTerms) {
            if (contractTerm.getItem().getCode().equals(itemCode)) {
                return contractTerm.getPrice();
            }
        }
        return -1;
    }

    public double getTotalPriceOfItems(String itemCode, int quantity) {
        getContractTerms() ;
        for (ContractTerm contractTerm : contractTerms) {
            if (contractTerm.getItem().getCode().equals(itemCode)) {
                return contractTerm.calculateTotalPrice(quantity);
            }
        }
        return -1;
    }

    public boolean addTerm(ContractTerm contractTerm) {
        ContractTermDTO c = new ContractTermDTO(contractTerm.getItem().toDTO() , contractTerm.getAmountSpecifier().toDTO() , contractTerm.getPrice() , id) ;
        if(!contractRepository.addContractTerm(c))
            throw new RuntimeException("sql problem");
//        contractTerms.add(contractTerm);
        return true;
    }

    public boolean updateTerm(String itemCode , AmountSpecifier amountSpecifier) {
        getContractTerms() ;
        ContractTerm term = null ;
        for (ContractTerm contractTerm : contractTerms) {
            if (itemCode.equals(contractTerm.getItem().getCode())) {
                term = contractTerm;
//                contractTerm.setAmountSpecifier(amountSpecifier);
                break;
            }
        }
        if(term == null)
            return false ;
        ContractTermDTO c = new ContractTermDTO(term.getItem().toDTO() , amountSpecifier.toDTO() , term.getPrice() , id) ;
        if(!contractRepository.updateContractTerm(c))
            throw new RuntimeException("sql problem");
        return true;
    }
}