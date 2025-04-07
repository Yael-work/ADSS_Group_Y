package Models.DBModels;

//@mohamed TODO complete the class
public class Supplier extends BaseModel {
    private String supplierName;

    public Supplier(int id, String supplierName) {
        super(id, Supplier.class.getName());
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierName='" + supplierName + '\'' +
                "} " + super.toString();
    }

}
