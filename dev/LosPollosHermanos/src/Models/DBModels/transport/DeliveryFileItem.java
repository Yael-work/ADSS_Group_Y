package Models.DBModels.transport;

import Models.DBModels.BaseModel;

public class DeliveryFileItem extends BaseModel {
    private int itemId; // FK
    private double amount;
    private double amountPickedUp;

    public DeliveryFileItem(int id, int itemId, double amount, double amountPickedUp) {
        super(id, DeliveryFileItem.class.getName());
        this.itemId = itemId;
        this.amount = amount;
        this.amountPickedUp = amountPickedUp;
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

    public double getAmountPickedUp() {
        return amountPickedUp;
    }

    public void setAmountPickedUp(double amountPickedUp) {
        this.amountPickedUp = amountPickedUp;
    }

    @Override
    public String toString() {
        return "DeliveryFileItem{" +
                "itemId=" + itemId +
                ", amount=" + amount +
                ", amountPickedUp=" + amountPickedUp +
                "} " + super.toString();
    }
}
