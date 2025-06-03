package DTO.Suppliers;

import Auxiliary.Enums.Models.Suppliers.DeliveryDays;
import Auxiliary.Enums.Models.Suppliers.SupplierType;
import Auxiliary.SupplierHelper;
import Models.DBModels.suppliers.ContactPerson;
import Models.DBModels.suppliers.PaymentTerms;

import java.util.ArrayList;

public class SupplierDTO {
    String name;
    String address;
    String phone;
    String number ;
    PaymentTermsDTO paymentTerms;
    SupplierType type ;
    ArrayList<DeliveryDays> deliveryDays ;

    public SupplierDTO(String name, String address, String phone, String number, PaymentTermsDTO paymentTerms , SupplierType type , ArrayList<DeliveryDays> deliveryDays) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.number = number;
        this.paymentTerms = paymentTerms;
        this.type = type;
        this.deliveryDays = deliveryDays;
    }

    public ArrayList<DeliveryDays> getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(ArrayList<DeliveryDays> deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public SupplierType getType() {
        return type;
    }

    public void setType(SupplierType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PaymentTermsDTO getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(PaymentTermsDTO paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    @Override
    public String toString() {
        return "Supplier{" + name + "," + address + "," + number + "," + paymentTerms.toString() + "}";
    }
}
