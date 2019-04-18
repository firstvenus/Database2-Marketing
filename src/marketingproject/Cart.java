/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketingproject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hatic
 */
public class Cart {
    
    
    private List<Integer> products ;
    
    public Cart(){
        products = new ArrayList<Integer>();
    }
    
    public void AddToCart(Integer id){
        this.products.add(id);
    }
    
    public List<Integer> GetProducts(){
        return this.products;
    }
}
