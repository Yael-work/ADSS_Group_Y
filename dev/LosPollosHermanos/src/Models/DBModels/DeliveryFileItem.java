package Models.DBModels;

public class DeliveryFileItem extends BaseModel {
    private int itemId; // FK
    private double amount;

    public DeliveryFileItem(int id, int itemId, double amount) {
        super(id, DeliveryFileItem.class.getName());
        this.itemId = itemId;
        this.amount = amount;
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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "DeliveryFileItem{" +
                "itemId=" + itemId +
                ", amount=" + amount +
                "} " + super.toString();
    }
}
