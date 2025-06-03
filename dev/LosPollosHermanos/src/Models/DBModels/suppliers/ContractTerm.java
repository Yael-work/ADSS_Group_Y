package Models.DBModels.suppliers;

public class ContractTerm {
    private Item item ;
    private AmountSpecifier amountSpecifier ;
    private double price ;

    public ContractTerm(ContractTerm other) {
        this.item = new Item(other.item) ;
        this.price = other.price ;
        this.amountSpecifier = new AmountSpecifier(other.amountSpecifier) ;
    }

    public ContractTerm(Item item, double price, AmountSpecifier amountSpecifier) {
        this.item = item;
        this.price = price;
        this.amountSpecifier = amountSpecifier;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AmountSpecifier getAmountSpecifier() {
        return amountSpecifier;
    }

    public void setAmountSpecifier(AmountSpecifier amountSpecifier) {
        this.amountSpecifier = amountSpecifier;
    }

    @Override
    public String toString() {
        return "{ " + item + " , " + price + " , " + amountSpecifier + " }";
    }

    public double calculateTotalPrice(int quantity) {
        return quantity * amountSpecifier.getCostPerUnit(quantity , price) ;
    }
}
