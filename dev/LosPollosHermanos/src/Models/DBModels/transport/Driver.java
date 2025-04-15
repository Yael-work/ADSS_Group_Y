package Models.DBModels.transport;

import Models.DBModels.BaseModel;

public class Driver extends BaseModel {
    private String firstName;
    private String lastName;
    private String personalId;
    private String phoneNumber;
    private String licenseType;

    public Driver(int id, String firstName, String lastName, String personalId, String phoneNumber, String licenseType) {
        super(id, Driver.class.getName());
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalId = personalId;
        this.phoneNumber = phoneNumber;
        this.licenseType = licenseType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personalId='" + personalId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", licenseType='" + licenseType + '\'' +
                "} " + super.toString();
    }
}
