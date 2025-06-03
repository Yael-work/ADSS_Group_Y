package DTO.Suppliers;

public class ItemDTO {
    private String code ;
    private String name;

    public ItemDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public ItemDTO(String str){
        String[] arr = str.split(",");
        this.code = arr[0];
        this.name = arr[1];
    }
    @Override
    public String toString() {
        return code + "," + name;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
