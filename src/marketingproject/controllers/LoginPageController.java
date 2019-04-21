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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import marketingproject.DataStore;
import marketingproject.DatabaseConnection;
import marketingproject.models.Employee;
import marketingproject.models.User;

public class LoginPageController implements Initializable {

    @FXML
    private TextField UserName_TextField;
    @FXML
    private PasswordField Password_TextField;
    @FXML
    private Button Login_Button;
    @FXML
    private CheckBox cb_employee_login;
    @FXML
    private Label lbl_error;
    
    private static DatabaseConnection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection = new DatabaseConnection();
        } catch (SQLException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Login_Button.setOnAction(e -> {
            try {
                Login(e);
            } catch (IOException ex) {
                Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    public void GotoProductPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/marketingproject/views/ProductPageView.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Store");
        window.show();
    }

    private void Login(ActionEvent e) throws IOException {
        String UserName = UserName_TextField.getText();
        String Password = Password_TextField.getText();

        if (cb_employee_login.isSelected()) {
            // Employee login
            Employee emp = connection.EmployeeLogin(UserName, Password);
            if(emp != null){
                // Employee login success
                DataStore.getInstance().setEmployee(emp);
                GotoEmployeepage(e);
            }
            else{
                // Username or password error
                lbl_error.setText("Username or password incorrect. Please try again");
            }
        } else {
            // User login
            User user = connection.UserLogin(UserName, Password);
            if (user != null) {
                // User login success
                DataStore.getInstance().setUser(user);
                GotoProductPage(e);

            } else {
                // Username or password error
                lbl_error.setText("Username or password incorrect. Please try again");
            }
        }
    }

    @FXML
    private void GoToSignUp(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/marketingproject/views/RegisterUser.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Register user");
        window.show();
    }

    private void GotoEmployeepage(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/marketingproject/views/EmployeePageView.fxml"));
        Parent parent = loader.load();        
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Employee Page");
        window.show();
    }
}
