package Models.DBModels.suppliers;

import Auxiliary.*;
import DTO.Suppliers.ContactPersonDTO;
import DTO.Suppliers.SupplierDTO;
import Auxiliary.Enums.Models.Suppliers.*;
import Domain.suppliers.ISuppliersRepository;
import Domain.suppliers.SuppliersRepository;

import java.util.ArrayList;
import java.util.Objects;

public class Supplier {
    protected String name;
    protected String address;
    protected String phone;
    protected String number ;
    protected PaymentTerms paymentTerms;
    protected ArrayList<ContactPerson> contactPersons;
    protected ArrayList<DeliveryDays> deliveryDays;
    protected SupplierType type;
    protected ISuppliersRepository supplierRepository = SuppliersRepository.getInstance();

    public Supplier(String name, String address, String phone , String number , PaymentTerms paymentTerms , ArrayList<DeliveryDays> deliveryDays) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.number = number;
        this.paymentTerms = new PaymentTerms(paymentTerms);
        this.contactPersons = new ArrayList<>();
        this.deliveryDays = new ArrayList<>();
        if(deliveryDays == null || deliveryDays.isEmpty())
            type = SupplierType.nonConstant ;
        else {
            this.deliveryDays = deliveryDays;
        }
    }

    public Supplier(SupplierDTO supplierDTO) {
        this.name = supplierDTO.getName();
        this.address = supplierDTO.getAddress();
        this.phone = supplierDTO.getPhone();
        this.number = supplierDTO.getNumber();
        this.paymentTerms = new PaymentTerms(supplierDTO.getPaymentTerms());
        this.contactPersons = new ArrayList<>();
        this.deliveryDays = supplierDTO.getDeliveryDays();
        this.type = supplierDTO.getType() ;
    }

    public void update(SupplierDTO supplierDTO) {
        this.name = supplierDTO.getName();
        this.address = supplierDTO.getAddress();
        this.phone = supplierDTO.getPhone();
        this.number = supplierDTO.getNumber();
        this.paymentTerms = new PaymentTerms(supplierDTO.getPaymentTerms());
        this.contactPersons = new ArrayList<>();
        this.deliveryDays = supplierDTO.getDeliveryDays();
        this.type = supplierDTO.getType() ;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Supplier){
            return Objects.equals(number, ((Supplier) o).getNumber());
        }
        return false;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSiteName() {
        return name;
    }

    public void setSiteName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PaymentTerms getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(PaymentTerms paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public ArrayList<ContactPerson> getContactPersons() {
        this.contactPersons = supplierRepository.getContacts(number) ;
        if(contactPersons == null)
            throw new RuntimeException("something went wrong in the sql execution") ;
        return contactPersons;
    }

    public void setContactPersons(ArrayList<ContactPerson> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Supplier{" + name + "," + address + "," + number + "," + paymentTerms.toString() + "}";
    }

    public String expandedToString(){
        String result = "Supplier{" + name + "," + address + "," + number + "," + paymentTerms.toString() + "}\n";
        ArrayList<ContactPersonDTO> arr = new ArrayList<>();
        for(ContactPerson c : getContactPersons()){
            arr.add(c.toDTO()) ;
        }
        result += SupplierHelper.contactPeopleToString(arr) ;
        return result;
    }

    public SupplierDTO toDTO(){
        return new SupplierDTO(name , address , phone , number , paymentTerms.toDTO() , type , deliveryDays) ;
    }
}
