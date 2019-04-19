package marketingproject.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import marketingproject.DataStore;
import marketingproject.DatabaseConnection;
import marketingproject.models.Category;
import marketingproject.models.Employee;
import marketingproject.models.Product;
import marketingproject.models.Supplier;

public class StockOperationController implements Initializable {
    
    @FXML private ChoiceBox<Category> cb_category;
    @FXML private ChoiceBox<Product> cb_product;
    @FXML private TextField txt_quantity;
    @FXML private Label lbl_status;
    @FXML private ChoiceBox<Supplier> cb_supplier;
    
    private DatabaseConnection connection;
    
    public Employee employee;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.employee = DataStore.getInstance().getEmployee();
        
        try {
            connection = new DatabaseConnection();
        } catch (SQLException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArrayList<Category> categories = connection.GetAllCategories();
        ObservableList<Category> categoriesList = FXCollections.observableArrayList(categories);
        cb_category.setItems(categoriesList);
        
        ArrayList<Supplier> suppliers = connection.GetAllSuppliers();
        ObservableList<Supplier> supplierList = FXCollections.observableArrayList(suppliers);
        cb_supplier.setItems(supplierList);
        
        cb_category.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        new ChangeListener<Category>() {
                    @Override
                    public void changed(ObservableValue<? extends Category> observable, Category oldValue, Category newValue) {
                        ArrayList<Product> products = connection.GetProductsByCategoryId(newValue.ID);
                        ObservableList<Product> pList = FXCollections.observableArrayList(products);
                        cb_product.setItems(pList);
                    }
                });
    }

    @FXML
    private void btn_back_event(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/marketingproject/views/EmployeePageView.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    private void btn_save_click_event(ActionEvent event) {
        Product selected_product = cb_product.getSelectionModel().getSelectedItem();
        Supplier selected_supplier = cb_supplier.getSelectionModel().getSelectedItem();
        String quantity_value = txt_quantity.getText();
        
        lbl_status.setTextFill(Color.RED);
        
        if (selected_product == null) {
            lbl_status.setText("Select Product");
            return;
        }
        
        if (selected_supplier == null) {
            lbl_status.setText("Select Supplier");
            return;
        }
        
        Integer quantity;
        try {
            quantity = Integer.parseInt(quantity_value);
            if(quantity == 0){
                lbl_status.setText("Quantity must be different from zero..");
                return;
            }
            
        } catch (NumberFormatException e) {
            lbl_status.setText("Please check quantity value, that must be integer");
            return;
        }
        
        boolean res = connection.AddStock(employee.ID, selected_product.ID, selected_supplier.ID,quantity);
        
        if(res){
            lbl_status.setTextFill(Color.GREEN);
            lbl_status.setText("Saved successfully ..");
        }else{
            lbl_status.setText("An error occured ..");
        }
        
    }
    
}
