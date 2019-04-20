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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import marketingproject.DatabaseConnection;
import marketingproject.models.OrderDetail;
import marketingproject.models.UnconfirmedOrder;
import marketingproject.models.User;

public class UnconfirmedOrdersController implements Initializable {

    @FXML private TableView<UnconfirmedOrder> tbl_unconfirmed_orders;
    @FXML private TableColumn<?, ?> col_userid;
    @FXML private TableColumn<?, ?> col_username;
    @FXML private TableColumn<?, ?> col_productcount;
    @FXML private TableColumn<?, ?> col_totalprice;
    @FXML private TableColumn<?, ?> col_totalkdv;
    @FXML private TableColumn<?, ?> col_orderdate;
    
    private DatabaseConnection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection = new DatabaseConnection();
        } catch (SQLException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArrayList<UnconfirmedOrder> result = connection.GetUnconfirmedOrders();

        if(result != null){
            col_userid.setCellValueFactory(new PropertyValueFactory<>("userid"));
            col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
            col_productcount.setCellValueFactory(new PropertyValueFactory<>("productcount"));
            col_totalprice.setCellValueFactory(new PropertyValueFactory<>("totalprice"));
            col_totalkdv.setCellValueFactory(new PropertyValueFactory<>("totalkdv"));
            col_orderdate.setCellValueFactory(new PropertyValueFactory<>("orderdate"));
            
            for (int i = 0; i < result.size(); i++) {
                tbl_unconfirmed_orders.getItems().add(result.get(i));
            }
        }
    }    

    @FXML
    private void btn_show_detail_event(ActionEvent event) throws IOException{
        UnconfirmedOrder selectedRow = tbl_unconfirmed_orders.getSelectionModel().getSelectedItem();
        
        if(selectedRow != null){
            Integer uid = selectedRow.getUserid();
            Integer oid = selectedRow.getOrderid();
            
            ArrayList<OrderDetail> odetail = connection.GetOrderDetail(oid);
            System.out.println(odetail.size());
            User user = connection.GetUserInfo(uid);            
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/marketingproject/views/UnconfirmedOrderDetail.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            
            UnconfirmedOrderDetailController c = loader.getController();
            c.setParams(user, odetail, oid);
            
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
 
        
        
    }

    @FXML
    private void btn_back_event(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/marketingproject/views/EmployeePageView.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
}
