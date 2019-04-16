/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketingproject;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class FXMLDocumentController implements Initializable{
    
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
    
    
    private static DatabaseConnection connection;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection = new DatabaseConnection();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Login_Button.setOnAction(e -> Login());
    }    
    
    private void Login(){
        String UserName = UserName_TextField.getText();
        String Password = Password_TextField.getText();
        if(connection.AdminLogin(UserName,Password)){
            System.err.println("Doğru Admin");
            //doğruysa ana sayfaya yönlendir
            //todo
        }
        else if(connection.UserLogin(UserName,Password)){
             System.err.println("Doğru kullanıcı");
             //doğruysa ana sayfaya yönlendir
            //todo
        }
        else{
            System.out.println("yanlış kullanıcı adı veya şifre");
//            //Böyle bir kullanıcı yok
//            //todo
        }
    }

    @FXML
    private void GoToSignUp(MouseEvent event) {
        //todo
    }
}
