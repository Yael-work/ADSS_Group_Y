package Models.DBModels.suppliers;

import DTO.Suppliers.ContactPersonDTO;

public class ContactPerson {
    private String name ;
    private String phone ;

    @Override
    public String toString() {
        return "{" + name + "," + phone + "}";
    }
    public ContactPerson(ContactPerson contactPerson) {
        this.name = contactPerson.getName();
        this.phone = contactPerson.getPhone();
    }
    public ContactPerson(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public ContactPerson(ContactPersonDTO contactPersonDTO) {
        this.name = contactPersonDTO.getName();
        this.phone = contactPersonDTO.getPhone();
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

    public ContactPersonDTO toDTO(){
        return new ContactPersonDTO(this.name, this.phone , null);
    }
}
