package marketingproject.models;


public class Product {
    public Integer ID;
    public String ProductName;
    public Integer Category_ID;
    public Float Price;
    public Integer Quantity;
    public Float KDV_Rate;
    public byte[] Image;
    
    public Product(Integer id,String productName){
        this.ID = id;
        this.ProductName = productName;
    }
    
    @Override
    public String toString(){
        return this.ProductName;
    }
}
