/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketingproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;




/**
 * FXML Controller class
 *
 * @author hatic
 */
public class ProductPageViewController implements Initializable {

    @FXML private VBox vbox_products;
    
    /**
     * Initializes the controller class.
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    @FXML
    public void loadProducts() throws IOException{
        for (int i = 0; i < 60; i++) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ProductContainer.fxml"));

            AnchorPane parent =  loader.load();      

            ProductContainerController controller = loader.getController();
            controller.LoadData(i,"Urun "+ i+1 , Integer.toString(i*10));
            vbox_products.getChildren().add(parent);
            Separator sp = new Separator();
            sp.prefWidth(350);
            sp.setLayoutX(50);
            vbox_products.getChildren().add(sp);
        }
    }
    
     
    
}
