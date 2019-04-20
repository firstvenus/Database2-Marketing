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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import marketingproject.DatabaseConnection;
import marketingproject.models.OrderDetail;
import marketingproject.models.User;

public class UnconfirmedOrderDetailController implements Initializable {

    @FXML private TableView<OrderDetail> tbl_detail;
    @FXML private TableColumn<?, ?> col_product_id;
    @FXML private TableColumn<?, ?> col_category;
    @FXML private TableColumn<?, ?> col_product_name;
    @FXML private TableColumn<?, ?> col_price;
    @FXML private TableColumn<?, ?> col_kdv;
    @FXML private Label lbl_name;
    @FXML private Label lbl_surname;
    @FXML private Label lbl_email;
    @FXML private Label lbl_country;
    @FXML private Label lbl_city;
    @FXML private Label lbl_addres;
    @FXML private Button btn_confirm;
    @FXML private Label lbl_status;

    private DatabaseConnection connection;
    private ArrayList<OrderDetail> orderDetail;
    private Integer order_id;
    private User user;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection = new DatabaseConnection();
        } catch (SQLException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    public void setParams(User user, ArrayList<OrderDetail> orderDetail,Integer order_id){
        this.user = user;
        this.orderDetail = orderDetail;
        this.order_id = order_id;
        
        col_product_id.setCellValueFactory(new PropertyValueFactory<>("productid"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_product_name.setCellValueFactory(new PropertyValueFactory<>("productname"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_kdv.setCellValueFactory(new PropertyValueFactory<>("kdv"));
        
        for (int i = 0; i < orderDetail.size(); i++) {
            tbl_detail.getItems().add(orderDetail.get(i));
        }
        
        lbl_name.setText(user.Name);
        lbl_surname.setText(user.Surname);
        lbl_email.setText(user.Email);
        lbl_country.setText(user.Country);
        lbl_city.setText(user.City);
        lbl_addres.setText(user.Address);
        
    }
    
    @FXML
    private void btn_back_event(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/marketingproject/views/UnconfirmedOrders.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void btn_confirm_event(ActionEvent event) {
        boolean res =  connection.ConfirmOrder(order_id);
        
        if(res){
            btn_confirm.setDisable(true);
        }else{
            lbl_status.setText("An error occured ..");
        }
    }
    
}
