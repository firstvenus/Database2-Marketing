/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketingproject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author hatic
 */
public class ProductContainerController implements Initializable {

    @FXML private ImageView img_product_image;
    @FXML private Label lbl_product_name;
    @FXML private Button btn_add_to_cart;
    @FXML private Label lbl_product_price;
    private Integer id;
    
    /**
     * Initializes the controller class.
     */
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
