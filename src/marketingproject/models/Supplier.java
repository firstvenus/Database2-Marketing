package marketingproject.models;

public class Supplier {
    public Integer ID;
    public String CompanyName;
    
    public Supplier(Integer id,String company_name){
        this.ID = id;
        this.CompanyName = company_name;
    }
    
    @Override
    public String toString(){
        return this.CompanyName;
    }
    
}

