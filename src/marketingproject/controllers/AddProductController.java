package marketingproject.controllers;

import java.io.File;
import java.io.FileInputStream;
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
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import marketingproject.DatabaseConnection;
import marketingproject.models.Category;
import marketingproject.models.Supplier;

public class AddProductController implements Initializable {

    @FXML private ChoiceBox<Category> cb_category_select;
    @FXML private ChoiceBox<Supplier> cb_supplier_select;
    @FXML private TextField txt_product_name;
    @FXML private TextField txt_kdv_Rate;
    @FXML private Label lbl_status;
    @FXML private TextField txt_price;
    
    private DatabaseConnection connection;

    private FileInputStream choosen_file;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection = new DatabaseConnection();
        } catch (SQLException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList categories = connection.GetAllCategories();
        ArrayList suppliers = connection.GetAllSuppliers();

        ObservableList<Category> categoriesList = FXCollections.observableArrayList(categories);
        ObservableList<Supplier> suppliersList = FXCollections.observableArrayList(suppliers);

        cb_category_select.setItems(categoriesList);
        cb_supplier_select.setItems(suppliersList);

    }

    @FXML
    private void btn_add_product_event(ActionEvent event) {
        Category selected_category = cb_category_select.getSelectionModel().getSelectedItem();
        Supplier selected_supplier = cb_supplier_select.getSelectionModel().getSelectedItem();
        String product_name = txt_product_name.getText();
        String kdv_rate = txt_kdv_Rate.getText();
        String price_value = txt_price.getText();
        Double kdv;
        Double price;

        lbl_status.setTextFill(Color.RED);
        if (selected_category == null) {
            lbl_status.setText("Please select category");
            return;
        }
        if (selected_supplier == null) {
            lbl_status.setText("Please select supplier");
            return;
        }
        if (product_name.isEmpty()) {
            lbl_status.setText("Please type product name");
            return;
        }

        try {
            price = Double.parseDouble(price_value);
        } catch (NumberFormatException e) {
            lbl_status.setText("Price value must be float number ..");
            return;
        }

        try {
            kdv = Double.parseDouble(kdv_rate);
        } catch (NumberFormatException e) {
            lbl_status.setText("KDV rate must be float number ..");
            return;
        }

        lbl_status.setText("");

        boolean result = false;
        try {
            result = connection.AddNewProduct(selected_category.ID, product_name, selected_supplier.ID, choosen_file , kdv, price);
        } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (result) {
            lbl_status.setTextFill(Color.GREEN);
            lbl_status.setText("Product added");
            txt_product_name.setText("");
            choosen_file = null;
        } else {
            lbl_status.setText("An error occured when saving product..");
        }
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
    private void btn_select_image_event(ActionEvent event) throws IOException{
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg","*.jpeg","*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(window);
        
        if(file != null){
            choosen_file = new FileInputStream(file);
        }
        else{
            System.out.println(" file emptry sdfsfd");
        }
    }
}
