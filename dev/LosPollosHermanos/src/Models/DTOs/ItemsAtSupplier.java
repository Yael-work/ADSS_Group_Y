package Models.DTOs;

public class ItemsAtSupplier {
    private int supplierId;
    private int itemId;
    private double amount;

    public ItemsAtSupplier(int supplierId, int itemId, double amount) {
        this.supplierId = supplierId;
        this.itemId = itemId;
        this.amount = amount;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ItemsAtSupplier{" +
                "supplierId=" + supplierId +
                ", itemId=" + itemId +
                ", amount=" + amount +
                "} ";
    }
}
