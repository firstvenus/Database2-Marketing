package marketingproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import marketingproject.models.Employee;
import marketingproject.models.Product;
import marketingproject.models.User;

public class DataStore {
    private static DataStore single_instance = null;

    private Employee employee;
    private User user;
    private final ArrayList<Product> cart_products = new ArrayList<>();
    private final List<CartListener> listeners = new ArrayList<CartListener>();

    private DataStore() {
    }

    public static DataStore getInstance() {
        if (single_instance == null) {
            single_instance = new DataStore();
        }

        return single_instance;
    }

    public void setEmployee(Employee emp){
        this.employee = emp;
    }
    
    public void setUser(User user){
        this.user = user;
    }
    
    public Employee getEmployee(){
        return employee;
    }
    
    public User getUser(){
        return user;
    }

    public void addToCart(Product product) {
        cart_products.add(product);

        listeners.forEach((listener) -> {
            listener.productAdded(cart_products, product);
        });
    }
    
    public ArrayList<Product> getCartProducts(){
        return cart_products;
    }
        
    public void removeFromCart(Product product) {
        for (int i = 0; i < cart_products.size(); i++) {
            if(Objects.equals(cart_products.get(i).ID, product.ID)){
                cart_products.remove(i);
            }
        }
        
        listeners.forEach((listener) -> {
            listener.productRemoved(cart_products, product);
        });
    }
    
    public void addListener(CartListener listener){
        listeners.add(listener);
    }

    public boolean IsProductInCart(Integer id){
        for (int i = 0; i < cart_products.size(); i++) {
            if(cart_products.get(i).ID == id){
                return true;
            }
        }
        return false;
    };
}