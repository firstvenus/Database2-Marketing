package marketingproject.models;


public class OrderDetail {
    private Integer product_id;
    private String category;
    private String product_name;
    private Float price;
    private Float kdv;
    
    public Integer getProductid(){
        return product_id;
    }
    
    public String getCategory(){
        return category;
    }
    
    public String getProductname(){
        return product_name;
    }
    
    public Float getPrice(){
        return price;
    }
    
    public Float getKdv(){
        return kdv;
    }
    
    public void setProductId(Integer product_id){
        this.product_id = product_id;
    }
    
    public void setCategory(String category){
        this.category = category;
    }
    
    public void setProductName(String productName){
        this.product_name = productName;
    }
    
    public void setPrice(Float price){
        this.price = price;
    }
    
    public void setKdv(Float kdv){
        this.kdv = kdv;
    }
    
}
