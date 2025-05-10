package Models.DBModels.suppliers;

import Models.DBModels.transport.APhysicalSite;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//@mohamed TODO complete the class
@Entity
@Table(name = "suppliers")
public class Supplier extends APhysicalSite {
    @Column(nullable = false)
    private String supplierName;
    @Column(nullable = false)
    private String supplierAddress;
    @Column(nullable = false)
    private String supplierPhone;

    public Supplier(int id, String supplierName, String supplierAddress, String supplierPhone) {
        super(id, Supplier.class.getName());
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierPhone = supplierPhone;
    }

    public Supplier() {
        super(0, Supplier.class.getName());
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
