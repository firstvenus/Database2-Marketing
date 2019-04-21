package marketingproject.models;


public class PurchaseType {
    public short ID;
    public String PurchaseType;
    
    @Override
    public String toString(){
        return this.PurchaseType;
    }
}
