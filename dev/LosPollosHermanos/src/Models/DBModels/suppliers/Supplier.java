package Models.DBModels.suppliers;

import Models.DBModels.transport.APhysicalSite;

//@mohamed TODO complete the class
public class Supplier extends APhysicalSite {
    private String supplierName;
    private String supplierAddress;
    private String supplierPhone;

    public Supplier(int id, String supplierName, String supplierAddress, String supplierPhone) {
        super(id, Supplier.class.getName());
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierPhone = supplierPhone;
    }

    @Override
    public String getSiteName() {
        return supplierName;
    }

    @Override
    public void setSiteName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierName='" + supplierName + '\'' +
                ", supplierAddress='" + supplierAddress + '\'' +
                ", supplierPhone='" + supplierPhone + '\'' +
                "} " + super.toString();
    }

}
