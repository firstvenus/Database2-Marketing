package marketingproject.models;

public class Category {
    public Integer ID;
    public String Name;
    
    public Category(Integer id,String name){
        this.ID = id;
        this.Name = name;
    }
    
    @Override
    public String toString(){
        return this.Name;
    }
    
}
