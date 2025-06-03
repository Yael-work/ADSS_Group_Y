package DTO.Suppliers;

import Auxiliary.Enums.Models.Suppliers.OrderStatus;
import Models.DBModels.suppliers.Date;

public class OrderDTO {
    String supplierNumber ;
    String address ;
    Date date ;
    String phone ;
    int id ;
    OrderStatus status ;

    public OrderDTO(String supplierNumber, String address, Date date, String phone, OrderStatus status) {
        this.supplierNumber = supplierNumber;
        this.address = address;
        this.date = date;
        this.phone = phone;
        this.id = -1;
        this.status = status;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
