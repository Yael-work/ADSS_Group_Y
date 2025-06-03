package DTO.Suppliers;

public class ContractTermDTO {
    private ItemDTO itemDTO;
    private AmountSpecifierDTO amountSpecifierDTO;
    private double price;
    private int contractID;

    public ContractTermDTO(ItemDTO itemDTO, AmountSpecifierDTO amountSpecifierDTO, double price , int contractID) {
        this.itemDTO = itemDTO;
        this.amountSpecifierDTO = amountSpecifierDTO;
        this.price = price;
        this.contractID = contractID;
    }

    public ContractTermDTO(String str) {
        String[] arr = str.split(",");
        this.itemDTO = new ItemDTO(arr[0],arr[1]);
        this.amountSpecifierDTO = new AmountSpecifierDTO(Integer.parseInt(arr[2]),Double.parseDouble(arr[3]));
        this.price = Double.parseDouble(arr[4]);
        this.contractID = Integer.parseInt(arr[5]);
    }

    @Override
    public String toString(){
        return itemDTO.toString() + "," + amountSpecifierDTO.toString() + "," + price + "," + contractID;
    }

    public int getContractID() {
        return contractID;
    }

    public void setContractID(int contractID) {
        this.contractID = contractID;
    }

    public ItemDTO getItemDTO() {
        return itemDTO;
    }

    public void setItemDTO(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }

    public AmountSpecifierDTO getAmountSpecifierDTO() {
        return amountSpecifierDTO;
    }

    public void setAmountSpecifierDTO(AmountSpecifierDTO amountSpecifierDTO) {
        this.amountSpecifierDTO = amountSpecifierDTO;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
