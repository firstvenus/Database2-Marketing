package marketingproject.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ProductContainerController implements Initializable {

    @FXML private ImageView img_product_image;
    @FXML private Label lbl_product_name;
    @FXML private Button btn_add_to_cart;
    @FXML private Label lbl_product_price;
    private Integer id;
    

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void LoadData(Integer id, String pn,String price) throws IOException{
        this.id = id;
        this.lbl_product_name.setText(pn);
        this.lbl_product_price.setText(price + " TL");
    }
    
}
