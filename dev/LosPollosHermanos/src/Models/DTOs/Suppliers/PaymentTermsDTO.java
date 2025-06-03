package DTO.Suppliers;

import Auxiliary.EnumConvertor;
import Auxiliary.Enums.Models.Suppliers.PaymentDeliveryTime;
import Auxiliary.Enums.Models.Suppliers.PaymentMethod;
import Models.DBModels.suppliers.PaymentTerms;

public class PaymentTermsDTO {
    PaymentMethod paymentMethod ;
    PaymentDeliveryTime paymentDeliveryTime ;
    String bankDetails ;

    public PaymentTermsDTO(PaymentMethod paymentMethod, PaymentDeliveryTime paymentDeliveryTime, String bankDetails) {
        this.paymentMethod = paymentMethod;
        this.paymentDeliveryTime = paymentDeliveryTime;
        this.bankDetails = bankDetails;
    }

    public PaymentTermsDTO(String str){
        String[] arr = str.split(",");
        this.paymentMethod = EnumConvertor.stringToPaymentMethod(arr[0]);
        this.paymentDeliveryTime = EnumConvertor.stringToPaymentDeliveryTime(arr[1]);
        this.bankDetails = arr[2];
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

    public String toString(){
        return EnumConvertor.enumToString(paymentMethod) + "," + EnumConvertor.enumToString(paymentDeliveryTime) + "," + bankDetails;
    }
}
