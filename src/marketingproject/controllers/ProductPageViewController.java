package marketingproject.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import marketingproject.DataStore;
import marketingproject.DatabaseConnection;
import marketingproject.models.Category;
import marketingproject.models.Product;
import marketingproject.models.User;
import marketingproject.CartListener;


public class ProductPageViewController implements Initializable {

    @FXML private VBox vbox_products;
    @FXML private VBox vbox_categories;
    @FXML private VBox vbox_cart;
    @FXML private Label lbl_total_price;
    @FXML private Label lbl_total_kdv;
    
    private User user;
    private DatabaseConnection connection;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb){
        try {
            connection = new DatabaseConnection();
        } catch (SQLException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        this.user = DataStore.getInstance().getUser();
        
        // Kullanıcı ödeme ekranından tekrar gelirse 
        loadCartProduct();
        loadTotals(DataStore.getInstance().getCartProducts());
        
        DataStore.getInstance().addListener(new CartListener(){
            @Override
            public void productAdded(ArrayList<Product> products, Product product) {
                loadTotals(products);
                loadCartProduct();
            }

            @Override
            public void productRemoved(ArrayList<Product> products, Product product)  {
                loadTotals(products);
                loadCartProduct();

            }
        });
          
        ArrayList<Category> categories = connection.GetAllCategories();
         
        for (int i = 0; i < categories.size(); i++) {
            Label l = new Label(categories.get(i).Name);
            l.setTextFill(Color.WHITE);
            l.setFont(Font.font("System", 18));
            l.setCursor(Cursor.HAND);

            l.setOnMouseClicked(event -> {
                String clickedCategory = ((Label) event.getSource()).getText();
                
                for (int j = 0; j < vbox_categories.getChildren().size(); j++) {
                    vbox_categories.getChildren().get(j).setStyle("-fx-underline: false");
                }
                l.setStyle("-fx-underline: true");

                ArrayList<Product> plist = connection.GetProductsByCategoryName(clickedCategory);
                
                vbox_products.getChildren().remove(0, vbox_products.getChildren().size());
                
                plist.forEach(p -> {
                    try {
                        loadProduct(p);
                    } catch (IOException ex) {
                        Logger.getLogger(ProductPageViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            });
            
            vbox_categories.getChildren().add(l);
        }
    }

    public void loadProduct(Product p) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/marketingproject/views/ProductContainer.fxml"));

        AnchorPane parent =  loader.load();      

        ProductContainerController controller = loader.getController();
        controller.LoadData(p);
        vbox_products.getChildren().add(parent);
        
        Separator sp = new Separator();
        sp.prefWidth(200);
        sp.setLayoutX(50);
        vbox_products.getChildren().add(sp);
        
    }
    
    public void loadCartProduct(){
        
        ArrayList<Product> plist = DataStore.getInstance().getCartProducts();
        vbox_cart.getChildren().remove(0, vbox_cart.getChildren().size());
        
        for (int i = 0; i < plist.size(); i++) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/marketingproject/views/CartProductItem.fxml"));
            AnchorPane parent = null;      
            try {
                parent = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(ProductPageViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            CartProductItemController controller = loader.getController();
            controller.LoadData(plist.get(i));
            vbox_cart.getChildren().add(parent);
        }
    }
    
    public void loadTotals(ArrayList<Product> products){
        Double total = 0.0;
        Double total_kdv = 0.0;
        for (Product p : products) {
            total += p.Price;
            total_kdv += p.Price * p.KDV_Rate / 100;
        }
        if(total != 0.0){
            total = (double) Math.round(total * 100) / 100;
            total_kdv = (double) Math.round(total_kdv * 100) / 100;
        }

        lbl_total_price.setText(total + " TL");
        lbl_total_kdv.setText(total_kdv + " TL");
    }
    
    @FXML 
    private void btn_payment_click_event(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/marketingproject/views/Payment.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
