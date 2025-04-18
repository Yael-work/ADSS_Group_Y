package Models.DBModels.transport;

import Models.DBModels.BaseModel;

public class DeliveryFileItem extends BaseModel {
    private int stockItemId; // FK
    private double amount;
    private double amountPickedUp;

    public DeliveryFileItem(int id, int stockItemId, double amount, double amountPickedUp) {
        super(id, DeliveryFileItem.class.getName());
        this.stockItemId = stockItemId;
        this.amount = amount;
        this.amountPickedUp = amountPickedUp;
    }

    public int getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(int stockItemId) {
        this.stockItemId = stockItemId;
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
                "stockItemId=" + stockItemId +
                ", amount=" + amount +
                ", amountPickedUp=" + amountPickedUp +
                "} " + super.toString();
    }
}
