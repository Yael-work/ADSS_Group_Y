package Models.DBModels.suppliers;

import Auxiliary.EnumConvertor;

import java.util.ArrayList;
import Auxiliary.Enums.Models.Suppliers.* ;
import DTO.Suppliers.OrderDTO;
import DTO.Suppliers.OrderItemDTO;
import Domain.suppliers.IOrdersRepository;
import Domain.suppliers.OrderRepository;

public class Order {
    private Supplier supplier;
    private String address;
    private Date date;
    private String phone;
    private int id;
    private OrderStatus status;
    private ArrayList<OrderItem> items;
    private static IOrdersRepository repo = OrderRepository.getInstance();

    public Order(Supplier supplier, String address, Date date, String phone, OrderStatus status , int id) {
        this.supplier = supplier;
        this.address = address;
        this.date = date;
        this.phone = phone;
        this.status = status;
        this.id = id;
        items = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Order) {
            Order order = (Order) obj;
            return order.getId() == this.getId();
        }
        return false;
    }

    public OrderDTO toDTO(){
        return new OrderDTO(supplier.getNumber() , address , date , phone , status) ;
    }
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        this.status = repo.getOrderById(id).getStatus();
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public ArrayList<OrderItem> getItems() {
        this.items = repo.getAllOrderItems(id);
        return items ;
    }

    public void setItems(ArrayList<OrderItem> items) {
        this.items = items;
    }

    public boolean doesContainItem(String itemCode) {
        for (OrderItem orderItem : getItems()) {
            if (orderItem.getItemCode().equals(itemCode)) {
                return true;
            }
        }
        return false;
    }

    public boolean addItem(String itemCode , int quantity , double totalPrice) {
        return repo.addOrderItem(new OrderItemDTO(itemCode , quantity ,totalPrice , id)) ;
    }

    public boolean isEmpty() {
        return getItems().isEmpty();
    }

    @Override
    public String toString() {
        return supplier.toString() + " , " +
                address + " , " +
                phone + " , " +
                date.toString() + " , "
                + id + " , " + EnumConvertor.enumToString(status);
    }

    public String expandedToString() {
        String result = this.toString() + "\n";
        for (OrderItem orderItem : items) {
            result += orderItem.toString() + "\n";
        }
        return result;
    }
}