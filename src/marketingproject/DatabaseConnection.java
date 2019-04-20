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
import marketingproject.models.Product;
import marketingproject.models.Supplier;
import marketingproject.models.User;

public class DatabaseConnection {

    public static Connection connection;

    public DatabaseConnection() throws SQLException {
        try {
            MysqlDataSource datasource = new MysqlDataSource();
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
}
