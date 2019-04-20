package marketingproject.models;

import java.util.Date;

public class UnconfirmedOrder {
    private Integer userid;
    private String username;
    private Integer productCount;
    private Float totalPrice;
    private Float totalKdv;
    private Date orderDate;
    private Integer orderID;
    
    public Integer getUserid(){
        return userid;
    }
    
    public Integer getOrderid(){
        return orderID;
    }
    
    public String getUsername(){
        return username;
    }
    
    public Integer getProductcount(){
        return productCount;
    }
    
    public Float getTotalprice(){
        return totalPrice;
    }
    
    public Float getTotalkdv(){
        return totalKdv;
    }
    
    public Date getOrderdate(){
        return orderDate;
    }
    
    public void setUserId(Integer userid){
        this.userid = userid;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setProductCount(Integer productCount){
        this.productCount = productCount;
    }
    
    public void setTotalPrice(Float totalPrice){
        this.totalPrice = totalPrice;
    }
    
    public void setTotalkdv(Float totalKdv){
        this.totalKdv = totalKdv;
    }
    
    public void setOrderDate(Date orderDate){
        this.orderDate = orderDate;
    }
    
    public void setOrderId(Integer orderid){
        this.orderID = orderid;
    }
}
