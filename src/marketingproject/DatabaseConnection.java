package marketingproject;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import marketingproject.models.Category;
import marketingproject.models.Employee;
import marketingproject.models.OrderDetail;
import marketingproject.models.Product;
import marketingproject.models.PurchaseType;
import marketingproject.models.Supplier;
import marketingproject.models.UnconfirmedOrder;
import marketingproject.models.User;

public class DatabaseConnection {

    public static Connection connection;
    MysqlDataSource datasource;
    public DatabaseConnection() throws SQLException {
        try {
            datasource = new MysqlDataSource();
            datasource.setUser("root");
            datasource.setPassword("1234");
            datasource.setPort(3306);
            datasource.setDatabaseName("market");
            connection = datasource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User UserLogin(String UserName, String Password) {
        User result = null;
        String SQL = "SELECT * FROM user WHERE Username = ? AND Password = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, UserName);
            statement.setString(2, Password);
            ResultSet rsLocal = statement.executeQuery();
            if (rsLocal.next()) {
                result = new User();
                result.ID = rsLocal.getInt("ID");
                result.Name = rsLocal.getString("Firstname");
                result.Surname = rsLocal.getString("Lastname");
                result.Username = rsLocal.getString("Username");
                result.Email = rsLocal.getString("Email");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Employee EmployeeLogin(String UserName, String Password) {
        Employee result = null;
        String SQL = "SELECT * FROM employee WHERE Username = ? AND Password = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, UserName);
            statement.setString(2, Password);
            ResultSet rsLocal = statement.executeQuery();
            if (rsLocal.next()) {
                result = new Employee();
                result.ID = rsLocal.getInt("ID");
                result.Name = rsLocal.getString("Firstname");
                result.Surname = rsLocal.getString("Lastname");
                result.Username = rsLocal.getString("Username");
                result.Email = rsLocal.getString("Email");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean Signup(String firstname, String lastname, String username, String password, String email, String country, String city, String address, String phone) {
        boolean result = false;
        String SQL = "CALL RegisterUser(?,?,?,?,?,?,?,?,?);";
        System.out.println("signup çalıştı 65 db");
        try {
            System.out.println("signup çalıştı 67 db");
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.setString(5, address);
            statement.setString(6, city);
            statement.setString(7, phone);
            statement.setString(8, country);
            statement.setString(9, email);

            statement.executeQuery();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        }
        return result;
    }

    public boolean AddCategory(String category) {
        boolean result = false;
        String SQL = "INSERT INTO category(Category) VALUES (?);";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, category);
            statement.executeUpdate();
            result = true;

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean AddNewProduct(Integer category_id, String product_name, FileInputStream image, Double kdv_rate,Double price) throws IOException{
        boolean result = false;
        String SQL = "INSERT INTO product(ProductName,Category_ID,Image,KDV_rate,Price) VALUES (?,?,?,?,?);";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, product_name);
            statement.setInt(2, category_id);
            if(image != null){
                statement.setBinaryStream(3, image,image.available());
            }
            else{
                 statement.setBinaryStream(3, null);
            }
            statement.setDouble(4, kdv_rate);
            statement.setDouble(5, price);
            statement.executeUpdate();
            result = true;

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean AddSupplier(String company_name) {
        boolean result = false;
        String SQL = "INSERT INTO supplier(CompanyName) VALUES (?);";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, company_name);
            statement.executeUpdate();
            result = true;

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public ArrayList<Category> GetAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        String SQL = "SELECT * FROM category";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsLocal = statement.executeQuery();
            while (rsLocal.next()) {
                categories.add(new Category(rsLocal.getInt("ID"), rsLocal.getString("Category")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    public ArrayList<Supplier> GetAllSuppliers() {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        String SQL = "SELECT * FROM supplier";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsLocal = statement.executeQuery();
            while (rsLocal.next()) {
                suppliers.add(new Supplier(rsLocal.getInt("ID"), rsLocal.getString("CompanyName")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return suppliers;
    }

    public ArrayList<Product> GetProductsByCategoryId(Integer category_id){
        ArrayList<Product> pList = new ArrayList<>();
        String SQL = "SELECT * FROM product WHERE Category_ID=?;";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, category_id);
            ResultSet rsLocal = statement.executeQuery();
            while (rsLocal.next()) {
                Product p = new Product(rsLocal.getInt("ID"), rsLocal.getString("ProductName"));
                p.Category_ID = rsLocal.getInt("Category_ID");
                p.Price = rsLocal.getFloat("Price");
                p.Quantity = rsLocal.getInt("Quantity");
                p.KDV_Rate = rsLocal.getFloat("KDV_rate");
                p.Image = rsLocal.getBytes("Image");
                
                pList.add(p);
            }
            
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return pList;
    }
    
    public boolean AddStock(Integer emp_id, Integer product_id, Integer supplier_id, Integer quantity) {
        boolean result = false;
        String SQL = "INSERT INTO stock_operation(Employee_ID,Product_ID,Supplier_ID,Quantity) VALUES (?,?,?,?);";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, emp_id);
            statement.setInt(2, product_id);
            statement.setInt(3, supplier_id);
            statement.setInt(4, quantity);
            statement.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ArrayList<UnconfirmedOrder> GetUnconfirmedOrders(){
        ArrayList<UnconfirmedOrder> result = null;
        String SQL = "SELECT * FROM view_incompleted_orders";
        try{
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsLocal = statement.executeQuery();
            result = new ArrayList<>();
            while(rsLocal.next()){
                UnconfirmedOrder order = new UnconfirmedOrder();
                order.setUserId(rsLocal.getInt("User_ID"));
                order.setOrderId(rsLocal.getInt("ID"));
                order.setUsername(rsLocal.getString("Username"));
                order.setTotalPrice(rsLocal.getFloat("Total_Price"));
                order.setTotalkdv(rsLocal.getFloat("Total_KDV"));
                order.setProductCount(rsLocal.getInt("OrderCount"));
                order.setOrderDate(rsLocal.getDate("Order_Time"));
                result.add(order);
            }
            
        }catch(SQLException e){
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
        
    }

    public ArrayList<OrderDetail> GetOrderDetail(Integer order_id){
        ArrayList<OrderDetail> result = null;
        String SQL = "SELECT * FROM view_order_detail WHERE Order_ID=?";
        try{
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, order_id);
            ResultSet rsLocal = statement.executeQuery();
            result = new ArrayList<>();
            while(rsLocal.next()){
                OrderDetail order = new OrderDetail();
                order.setProductId(rsLocal.getInt("Product_ID"));
                order.setCategory(rsLocal.getString("Category"));
                order.setProductName(rsLocal.getString("ProductName"));
                order.setPrice(rsLocal.getFloat("Price"));
                order.setKdv(rsLocal.getFloat("KDV"));                
                result.add(order);
            }
        }catch(SQLException e){
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
        
    }
    
    public User GetUserInfo(Integer uid) {
        User result = null;
        String SQL = "SELECT * FROM user WHERE ID=?";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, uid);
            ResultSet rsLocal = statement.executeQuery();
            if (rsLocal.next()) {
                result = new User();
                result.ID = rsLocal.getInt("ID");
                result.Name = rsLocal.getString("Firstname");
                result.Surname = rsLocal.getString("Lastname");
                result.Username = rsLocal.getString("Username");
                result.Email = rsLocal.getString("Email");
                result.Country = rsLocal.getString("Country");
                result.City = rsLocal.getString("City");
                result.Address = rsLocal.getString("Address");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public boolean ConfirmOrder(Integer oid){
        boolean result = false;
        String SQL = "UPDATE orders SET Is_Completed=True WHERE ID=?";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, oid);
            Integer res = statement.executeUpdate();
            if (res == 1){
                result = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ArrayList<Product> GetProductsByCategoryName(String category_name){
        ArrayList<Product> pList = new ArrayList<>();
        String SQL = "SELECT p.* FROM product AS p INNER JOIN category AS c ON c.ID=p.Category_ID WHERE c.Category=?;";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, category_name);
            ResultSet rsLocal = statement.executeQuery();
            while (rsLocal.next()) {
                Product p = new Product(rsLocal.getInt("ID"), rsLocal.getString("ProductName"));
                p.Category_ID = rsLocal.getInt("Category_ID");
                p.Price = rsLocal.getFloat("Price");
                p.Quantity = rsLocal.getInt("Quantity");
                p.KDV_Rate = rsLocal.getFloat("KDV_rate");
                p.Image =  rsLocal.getBytes("Image");
                pList.add(p);
            }
            
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return pList;
    }
    
    public ArrayList<PurchaseType> GetPaymentTypes(){
        ArrayList<PurchaseType> pList = new ArrayList<>();
        String SQL = "SELECT * FROM purchase_type;";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsLocal = statement.executeQuery();
            while (rsLocal.next()) {
                PurchaseType p = new PurchaseType();
                p.ID = rsLocal.getShort("ID");
                p.PurchaseType = rsLocal.getString("PurchaseType");
                pList.add(p);
            }
            
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return pList;
    }
 
    public Integer MakePayment(ArrayList<Product> products, Integer userid, Integer paymentid){
        String sql_order_insert = "INSERT INTO orders(User_ID,Total_Price,Total_KDV) VALUES(?,0.0,0.0);";
        String sql_select_last_insert_id = "SELECT LAST_INSERT_ID() as id;";
        String sql_insert_order_detail = "INSERT INTO order_detail(Order_ID,Product_ID,Price,KDV) VALUES(?,?,?,?);";
        String sql_insert_payment = "INSERT INTO purchase(Order_ID, Purchase_Type_ID) VALUES (?,?);";
        // ınsert an order
        Integer order_id = 0;
        try{
            PreparedStatement statement = connection.prepareStatement(sql_order_insert, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, userid);
            statement.executeUpdate();
            statement = connection.prepareStatement(sql_select_last_insert_id, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet res =  statement.executeQuery();
            res.next();
            order_id = res.getInt("id");
            
            for(Product p: products){
                statement = connection.prepareStatement(sql_insert_order_detail, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                Float kdv = p.Price * (p.KDV_Rate/100);
                statement.setInt(1, order_id);
                statement.setInt(2, p.ID);
                statement.setDouble(3, p.Price);
                statement.setDouble(4, (double)Math.round(kdv*100)/100);
                statement.executeUpdate();
            }
            
            statement = connection.prepareStatement(sql_insert_payment, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, order_id);
            statement.setInt(2, paymentid);
            statement.executeQuery();
            
        }catch(SQLException e){
            System.out.println(e);
        }
        return order_id;
    }   
}
