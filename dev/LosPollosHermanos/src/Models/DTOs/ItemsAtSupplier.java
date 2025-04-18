package Models.DTOs;

public class ItemsAtSupplier {
    private int supplierId;
    private int itemId;
    private double amount;
    private String barcode;

    public ItemsAtSupplier(int supplierId, int itemId, double amount, String barcode) {
        this.supplierId = supplierId;
        this.itemId = itemId;
        this.amount = amount;
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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
