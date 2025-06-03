package Models.DBModels.suppliers;

import DTO.Suppliers.ItemDTO;

public class Item {
    String code ;
    String name ;

    public Item(Item other){
        this.code = other.code;
        this.name = other.name;
    }

    public Item(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Item){
            return this.code.equals(((Item) obj).code);
        }
        return false ;
    }

    @Override
    public String toString() {
        return "{" + code + "," + name + " }";
    }

    public ItemDTO toDTO(){
        return new ItemDTO(code,name);
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
