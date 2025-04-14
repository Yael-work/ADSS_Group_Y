package Models.DBModels.Stock;

public class SizeCategory {
    private int sizeCategoryID;
    private String name;
    private ProductSubCategory parentCategory;


    public SizeCategory(int sizeCategoryID, String name, ProductSubCategory parentCategory) {
        this.sizeCategoryID = sizeCategoryID;
        this.name = name;
        this.parentCategory = parentCategory;
    }


    public int getSizeCategoryID() {
        return sizeCategoryID;
    }
    public void setSizeCategoryID(int sizeCategoryID) {
        this.sizeCategoryID = sizeCategoryID;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public ProductSubCategory getPerentCategory() {
        return parentCategory;
    }
    public void setPerentCategory(ProductSubCategory perentCategory) {
        this.parentCategory = perentCategory;
    }
}
