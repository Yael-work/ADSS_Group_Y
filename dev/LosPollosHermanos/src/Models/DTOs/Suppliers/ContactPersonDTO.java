package DTO.Suppliers;

import java.util.Objects;

public class ContactPersonDTO {
    String name ;
    String phone ;
    String suppNumber ;

    public ContactPersonDTO(String name, String phone , String suppNumber) {
        this.name = name;
        this.phone = phone;
        this.suppNumber = suppNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getSuppNumber() {
        return suppNumber;
    }
    public void setSuppNumber(String suppNumber) {
        this.suppNumber = suppNumber;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactPersonDTO that = (ContactPersonDTO) o;
        return Objects.equals(that.suppNumber, this.suppNumber) && Objects.equals(name, that.name) && Objects.equals(phone, that.phone);
    }

    @Override
    public String toString() {
        return "{" + name + "," + phone + "}";
    }
}
