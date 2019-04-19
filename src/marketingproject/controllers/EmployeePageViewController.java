package marketingproject.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import marketingproject.models.Employee;

public class EmployeePageViewController implements Initializable {

    private Employee employee;
    
    @FXML public Label lbl_employee_info;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setEmployee(Employee employee){
        this.employee = employee;
        lbl_employee_info.setText(employee.toString());
    }

    @FXML
    private void btn_show_incompleted_orders_Event(ActionEvent event) {
    }

    @FXML
    private void btn_add_new_product_event(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/marketingproject/views/AddProduct.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void btn_add_new_category_event(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/marketingproject/views/AddCategoryView.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void btn_add_new_supplier_event(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/marketingproject/views/AddSupplierView.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void btn_Add_stock_event(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/marketingproject/views/StockOperation.fxml"));
        Parent parent = loader.load();
        
        StockOperationController soc = loader.getController();
        soc.setEmployee(employee);
        
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}
