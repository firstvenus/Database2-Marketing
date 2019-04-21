package marketingproject.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import marketingproject.DataStore;
import marketingproject.models.Product;
import marketingproject.CartListener;

public class ProductContainerController implements Initializable {

    @FXML private ImageView img_product_image;
    @FXML private Label lbl_product_name;
    @FXML private Button btn_add_to_cart;
    @FXML private Label lbl_product_price;
    
    private DataStore datastore;
    private Product product;
    private boolean is_added;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        datastore = DataStore.getInstance();
        is_added = false;
    }    
    
    @FXML
    public void LoadData(Product product) throws IOException{
        this.product = product;
        
        if(product.Quantity == 0){
            btn_add_to_cart.setStyle("-fx-background-color: #acacac");
            btn_add_to_cart.setDisable(true);
            btn_add_to_cart.setText("Stock out");
        }
        
        if(datastore.IsProductInCart(product.ID)){
            System.out.println("marketingproject.controllers.ProductContainerController.LoadData()");
            is_added = true;
            btn_add_to_cart.setStyle("-fx-background-color: #ff4f00");
            btn_add_to_cart.setText("Remove from cart");
        }
        
        lbl_product_name.setText(product.ProductName);
        lbl_product_price.setText(product.Price + " TL");
        
        DataStore.getInstance().addListener(new CartListener(){
            @Override
            public void productAdded(ArrayList<Product> products, Product product) {

            }

            @Override
            public void productRemoved(ArrayList<Product> products, Product p)  {
                if(Objects.equals(product.ID, p.ID)){
                    is_added = false;
                    btn_add_to_cart.setStyle("-fx-background-color: #FF8600");
                    btn_add_to_cart.setText("Add to cart");
                }
            }
        });
                
        if(product.Image != null){
            img_product_image.setImage(new Image(new ByteArrayInputStream(product.Image)));
        }
    }
    
    @FXML
    public void btn_add_to_cart_event(ActionEvent event) {
        if (product.Quantity > 0) {
            if (is_added) {
                is_added = false;
                datastore.removeFromCart(product);
                btn_add_to_cart.setStyle("-fx-background-color: #FF8600");
                btn_add_to_cart.setText("Add to cart");
            } else {
                is_added = true;
                datastore.addToCart(product);
                btn_add_to_cart.setStyle("-fx-background-color: #ff4f00");
                btn_add_to_cart.setText("Remove from cart");
            }
        }
    }
    
}
