package DTO.Suppliers;

public class AmountSpecifierDTO {
    int minimalAmount ;
    double baseDiscount ;

    public AmountSpecifierDTO(int minimalAmount, double baseDiscount) {
        this.minimalAmount = minimalAmount;
        this.baseDiscount = baseDiscount;
    }
    public AmountSpecifierDTO(String str) {
        String[] arr = str.split(",");
        this.minimalAmount = Integer.parseInt(arr[0]);
        this.baseDiscount = Double.parseDouble(arr[1]);
    }
    @Override
    public String toString() {
        return minimalAmount + "," + baseDiscount;
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
