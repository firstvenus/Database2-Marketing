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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import marketingproject.DatabaseConnection;

public class RegisterUserController implements Initializable {

    @FXML
    private TextField txt_firstname;
    @FXML
    private TextField txt_lastname;
    @FXML
    private TextField txt_username;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_password;
    @FXML
    private TextField txt_country;
    @FXML
    private TextField txt_city;
    @FXML
    private TextArea txt_address;
    @FXML
    private Label lbl_error;
    @FXML
    private TextField txt_phone;

    private static DatabaseConnection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection = new DatabaseConnection();
        } catch (SQLException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btn_sign_up_click_event(ActionEvent event) {
        String firstname = txt_firstname.getText();
        String lastname = txt_lastname.getText();
        String username = txt_username.getText();
        String password = txt_password.getText();
        String email = txt_email.getText();
        String country = txt_country.getText();
        String city = txt_city.getText();
        String address = txt_address.getText();
        String phone = txt_phone.getText();

        if (firstname.isEmpty()
                || lastname.isEmpty()
                || username.isEmpty()
                || email.isEmpty()
                || password.isEmpty()) {

            lbl_error.setText("Please fill required fields");
            return;
        }
        try {
            boolean result = connection.Signup(firstname, lastname, username, password, email, country, city, address, phone);
            if (result){
                goto_login_page(event);
            }
        } catch (Exception e) {
             lbl_error.setText("An error occured, when sign up");
        }
        
    }

    private void goto_login_page(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/marketingproject/views/LoginPage.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Login Page");
        window.show();
    }
    
    @FXML
    private void btn_back_to_login_click_event(ActionEvent event) throws IOException {
        goto_login_page(event);
    }

}
