package Models.DBModels.Stock;

public class ProductSubCategory {
    private int subCategoryID;
    private String name;
    private ProductCategory perentCategory;

    public ProductSubCategory(int subCategoryID, String name, ProductCategory perentCategory) {
        this.subCategoryID = subCategoryID;
        this.name = name;
        this.perentCategory = perentCategory;
    }

    public int getSubCategoryID() {
        return subCategoryID;
    }
    public void setSubCategoryID(int subCategoryID) {
        this.subCategoryID = subCategoryID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ProductCategory getPerentCategory() {
        return perentCategory;
    }
    public void setPerentCategory(ProductCategory perentCategory) {
        this.perentCategory = perentCategory;
    }
}
