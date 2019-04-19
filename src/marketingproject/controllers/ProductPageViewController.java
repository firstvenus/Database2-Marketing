package marketingproject.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import marketingproject.DataStore;
import marketingproject.models.User;


public class ProductPageViewController implements Initializable {

    @FXML private VBox vbox_products;
    
    private User user;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
          this.user = DataStore.getInstance().getUser();
    }
    
    @FXML
    public void loadProducts() throws IOException{
        for (int i = 0; i < 60; i++) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/marketingproject/views/ProductContainer.fxml"));

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
