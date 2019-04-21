package marketingproject.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import marketingproject.DataStore;
import marketingproject.DatabaseConnection;
import marketingproject.models.Product;
import marketingproject.models.PurchaseType;


public class PaymentController implements Initializable {

    @FXML private Label lbl_product_count;
    @FXML private Label lbl_total_price;
    @FXML private Label lbl_total_kdv;
    @FXML private ChoiceBox<PurchaseType> cb_payment_type;
    @FXML private Label lbl_status;
    
    private DatabaseConnection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection = new DatabaseConnection();
        } catch (SQLException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArrayList ptypes = connection.GetPaymentTypes();
        ObservableList<PurchaseType> ptypelist = FXCollections.observableArrayList(ptypes);
        cb_payment_type.setItems(ptypelist);
        
        ArrayList<Product> products = DataStore.getInstance().getCartProducts();
                
        Double total = 0.0;
        Double total_kdv = 0.0;
        Integer count = 0;
        
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            count += 1;
            total += p.Price;
            total_kdv += p.Price * (p.KDV_Rate /100);
        }
        
        total = (double)Math.round(total*100)/100;
        total_kdv = (double)Math.round(total_kdv*100)/100;
        
        lbl_product_count.setText(count.toString());
        lbl_total_price.setText(total.toString());
        lbl_total_kdv.setText(total_kdv.toString());        
    }    

    @FXML
    private void btn_pay_click_event(ActionEvent event) throws IOException{
        PurchaseType ptype = cb_payment_type.getSelectionModel().getSelectedItem();
        if(ptype != null){
            connection.MakePayment(
                    DataStore.getInstance().getCartProducts(), 
                    DataStore.getInstance().getUser().ID, 
                    (int)ptype.ID);
        }
        else{
            lbl_status.setText("Please select payment type ..");
        }
    }
    
    @FXML
    private void btn_back_click_event(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/marketingproject/views/ProductPageView.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
}
