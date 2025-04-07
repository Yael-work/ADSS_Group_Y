package Models.DBModels;

public class Branch extends BaseModel {
    private String branchName;
    private String address;
    private String phone;

    public Branch(int id, String branchName, String address, String phone) {
        super(id, Branch.class.getName());
        this.branchName = branchName;
        this.address = address;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
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
}
