package Models.DBModels.suppliers;

public class OrderItem {
    private String itemCode;
    private int quantity;
    private double totalPrice;

    public OrderItem(String itemCode, int quantity , double totalPrice) {
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.totalPrice = totalPrice ;
    }

    @Override
    public String toString() {
        return "{" + itemCode + " , " + quantity + "," + totalPrice + "}";
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
