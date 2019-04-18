/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketingproject;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Label SignIn_Label;
    @FXML
    private TextField UserName_TextField;
    @FXML
    private PasswordField Password_TextField;
    @FXML
    private Button Login_Button;
    @FXML
    private Label SignUp_Label;
    @FXML
    private CheckBox cb_employee_login;
    @FXML
    private Label lbl_error;
    
    @FXML
    private AnchorPane rootPane;

    private static DatabaseConnection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection = new DatabaseConnection();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Login_Button.setOnAction(e -> {
            try {
                Login(e);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    public void GotoProductPage(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ProductPageView.fxml"));

        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        ProductPageViewController controller = loader.getController();
        controller.loadProducts();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();

    }

    private void Login(ActionEvent e) throws IOException {
        String UserName = UserName_TextField.getText();
        String Password = Password_TextField.getText();

        if (cb_employee_login.isSelected()) {
            // Employee login
            if(connection.EmployeeLogin(UserName, Password)){
                // Employee login success
            }
            else{
                // Username or password error
                lbl_error.setText("Username or password incorrect. Please try again");
            }
        } else {
            // User login
            if (connection.UserLogin(UserName, Password)) {
                // User login success
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
        loader.setLocation(getClass().getResource("RegisterUser.fxml"));

        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
}
