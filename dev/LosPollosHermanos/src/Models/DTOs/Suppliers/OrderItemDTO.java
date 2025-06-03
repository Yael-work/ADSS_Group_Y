package DTO.Suppliers;

public class OrderItemDTO {
    String itemCode ;
    int quantity;
    double totalPrice;
    int orderID;

    public OrderItemDTO(String itemCode, int quantity, double totalPrice, int orderID) {
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderID = orderID;
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

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}
