package marketingproject;

import java.util.ArrayList;
import marketingproject.models.Product;

public interface CartListener{
    void productAdded(ArrayList<Product> products, Product p);
    void productRemoved(ArrayList<Product> products, Product p);
}