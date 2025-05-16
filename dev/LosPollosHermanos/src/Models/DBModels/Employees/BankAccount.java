package Models.DBModels.Employees;

public class BankAccount {
    private final String bankName;
    private final String accountNumber;
    private final String branch;

    public BankAccount(String bankName, String accountNumber, String branch) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "Bank Information= \n" +
                "bankName= " + bankName +
                ", accountNumber= " + accountNumber +
                ", branch= " + branch;
    }

}
