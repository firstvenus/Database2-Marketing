package marketingproject.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import marketingproject.DatabaseConnection;

public class AddCategoryViewController implements Initializable {

    @FXML
    private TextField txt_category;
    @FXML
    private Label lbl_status_message;    
    
    private DatabaseConnection connection;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection = new DatabaseConnection();
        } catch (SQLException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void btn_back_button_event(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/marketingproject/views/EmployeePageView.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void btn_add_category_button_event(ActionEvent event) throws IOException{
        String category = txt_category.getText();
        
        if(category.isEmpty()){
            lbl_status_message.setText("Please write a category name...");
            lbl_status_message.setTextFill(Color.RED);
            return;
        }
        
        if(connection.AddCategory(category)){
            lbl_status_message.setText("Category add success ...");
            lbl_status_message.setTextFill(Color.GREEN);
            txt_category.setText("");
        }
        else{
            lbl_status_message.setText("Category add error ...");
            lbl_status_message.setTextFill(Color.RED);
        }
    }    
}
