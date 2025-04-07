package Models.DTOs;


public class StockItem {
    private int itemId;
    private String itemName;
    private int branchId;
    private double amount;

    public StockItem(int itemId, String itemName, int branchId, double amount) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.branchId = branchId;
        this.amount = amount;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "StockItem{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", branchId=" + branchId +
                ", amount=" + amount +
                "} ";
    }
}
