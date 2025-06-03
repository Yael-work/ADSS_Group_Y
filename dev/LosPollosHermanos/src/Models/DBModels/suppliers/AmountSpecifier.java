package Models.DBModels.suppliers;


import DTO.Suppliers.AmountSpecifierDTO;

public class AmountSpecifier {
    int minimalAmount ;
    double baseDiscount ;

    public AmountSpecifier(AmountSpecifier other){
        this.minimalAmount = other.minimalAmount;
        this.baseDiscount = other.baseDiscount;
    }

    public AmountSpecifier(int minimalAmount, double baseDiscount) {
        this.minimalAmount = minimalAmount;
        this.baseDiscount = baseDiscount;
    }

    @Override
    public String toString() {
        return "{" + minimalAmount + "," + baseDiscount + "}";
    }

    public double getCostPerUnit(int amount , double basePrice){
        if(amount >= minimalAmount)
            return basePrice - basePrice*getDiscount(amount);
        return basePrice ;
    }

    public AmountSpecifierDTO toDTO(){
        return new AmountSpecifierDTO(minimalAmount, baseDiscount);
    }
    public double getDiscount(int amount){
        return baseDiscount*(1 + Math.max(0,Math.atan((double) (amount - minimalAmount) / minimalAmount)) / 10) ;
    }

    public int getMinimalAmount() {
        return minimalAmount;
    }

    public void setMinimalAmount(int minimalAmount) {
        this.minimalAmount = minimalAmount;
    }

    public double getBaseDiscount() {
        return baseDiscount;
    }

    public void setBaseDiscount(double baseDiscount) {
        this.baseDiscount = baseDiscount;
    }

}
