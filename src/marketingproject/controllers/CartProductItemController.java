package marketingproject.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import marketingproject.DataStore;
import marketingproject.models.Product;

public class CartProductItemController implements Initializable {

    @FXML private Label lbl_product_name;
    @FXML private Label lbl_price;
    
    private Product product;
    private DataStore store;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        store = DataStore.getInstance();
    }    

    public void LoadData(Product p){
        this.product = p;
        lbl_price.setText(product.Price +" TL");
        lbl_product_name.setText(product.ProductName);
    }
    
    @FXML
    private void btn_remove_button_click_event(ActionEvent event) {
        store.removeFromCart(product);
    }
    
}
