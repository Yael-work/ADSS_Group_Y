package Models.DBModels.suppliers;

import Auxiliary.Enums.Models.Suppliers.* ;
import Auxiliary.EnumConvertor;
import DTO.Suppliers.PaymentTermsDTO;

public class PaymentTerms {
    private PaymentMethod paymentMethod;
    private PaymentDeliveryTime paymentDeliveryTime;
    private String bankDetails ;

    public PaymentTerms(PaymentTerms other){
        this.paymentMethod = other.paymentMethod;
        this.paymentDeliveryTime = other.paymentDeliveryTime;
        this.bankDetails = other.bankDetails;
    }

    public PaymentTerms(PaymentMethod paymentMethod, PaymentDeliveryTime paymentDeliveryTime , String bankDetails) {
        this.paymentMethod = paymentMethod;
        this.paymentDeliveryTime = paymentDeliveryTime;
        this.bankDetails = bankDetails;
    }

    public PaymentTerms(PaymentTermsDTO paymentTermsDTO) {
        this.paymentMethod = paymentTermsDTO.getPaymentMethod();
        this.paymentDeliveryTime = paymentTermsDTO.getPaymentDeliveryTime();
        this.bankDetails = paymentTermsDTO.getBankDetails();
    }

    @Override
    public String toString() {
        return "{" + EnumConvertor.enumToString(paymentMethod) + "," +
                EnumConvertor.enumToString(paymentDeliveryTime) + "," +
                bankDetails + "}" ;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentDeliveryTime getPaymentDeliveryTime() {
        return paymentDeliveryTime;
    }

    public void setPaymentDeliveryTime(PaymentDeliveryTime paymentDeliveryTime) {
        this.paymentDeliveryTime = paymentDeliveryTime;
    }

    public String getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(String bankDetails) {
        this.bankDetails = bankDetails;
    }

    public PaymentTermsDTO toDTO(){
        return new PaymentTermsDTO(paymentMethod,paymentDeliveryTime,bankDetails);
    }
}
