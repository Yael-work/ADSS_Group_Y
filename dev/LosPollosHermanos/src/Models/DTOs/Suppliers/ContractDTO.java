package DTO.Suppliers;

public class ContractDTO {
    int id ;
    String supplierNumber ;

    public ContractDTO(int id, String supplierNumber) {
        this.id = id;
        this.supplierNumber = supplierNumber;
    }
    public ContractDTO(String str){
        String[] arr = str.split(",");
        this.id = Integer.parseInt(arr[0]);
        this.supplierNumber = arr[1];
    }

    @Override
    public String toString() {
        return id + "," + supplierNumber;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }
}
