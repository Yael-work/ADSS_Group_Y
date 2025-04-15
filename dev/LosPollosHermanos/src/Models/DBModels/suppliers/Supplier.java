package Models.DBModels.suppliers;

import Models.DBModels.transport.APhysicalSite;

//@mohamed TODO complete the class
public class Supplier extends APhysicalSite {
    private String supplierName;

    public Supplier(int id, String supplierName) {
        super(id, Supplier.class.getName());
        this.supplierName = supplierName;
    }

    @Override
    public String getSiteName() {
        return supplierName;
    }

    @Override
    public void setSiteName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierName='" + supplierName + '\'' +
                "} " + super.toString();
    }

}
