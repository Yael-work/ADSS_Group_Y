package Models.DBModels.transport;

import Auxiliary.Helper;
import Models.DBModels.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "branches")
public class Branch extends APhysicalSite {
    @Column(nullable = false, unique = true)
    private String branchName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, unique = true)
    private String phone;

    public Branch(int id, String branchName, String address, String phone) {
        super(id, Branch.class.getName());
        this.branchName = branchName;
        this.address = address;
        this.phone = phone;
    }

    public Branch() {
        super(0, Branch.class.getName());
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getSiteName() {
        return branchName;
    }

    @Override
    public void setSiteName(String branchName) {
        this.branchName = branchName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", branchName='" + branchName + '\'' +
                "} " + super.toString();
    }

    /**
     * Copies the properties of another Branch object to this one.
     *
     * @param other The other Branch object to copy from.
     */
    public boolean copyFromOther(BaseModel other) {
        if (!(other instanceof Branch otherBranch)) {
            Helper.showError(String.format("%s::Other must be of type Branch", this.getClass().getName()));
            return false;
        }
        this.branchName = otherBranch.branchName;
        this.address = otherBranch.address;
        this.phone = otherBranch.phone;
        return true;
    }
}
