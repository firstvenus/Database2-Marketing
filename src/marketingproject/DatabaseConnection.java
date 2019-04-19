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
import marketingproject.models.Supplier;

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

    public boolean UserLogin(String UserName, String Password) {
        boolean result = false;
        String SQL = "SELECT * FROM user WHERE Username = ? AND Password = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, UserName);
            statement.setString(2, Password);
            ResultSet rsLocal = statement.executeQuery();
            rsLocal.absolute(0);
            if (rsLocal.next()) {
                result = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean EmployeeLogin(String UserName, String Password) {
        boolean result = false;
        String SQL = "SELECT * FROM employee WHERE Username = ? AND Password = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, UserName);
            statement.setString(2, Password);
            ResultSet rsLocal = statement.executeQuery();
            rsLocal.absolute(0);
            if (rsLocal.next()) {
                result = true;
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

    public boolean AddNewProduct(Integer category_id, String product_name, Integer supplier_id, FileInputStream image, Double kdv_rate,Double price) throws IOException{
        boolean result = false;
        String SQL = "INSERT INTO product(ProductName,Category_ID,Supplier_id,Image,KDV_rate,Price) VALUES (?,?,?,?,?,?);";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, product_name);
            statement.setInt(2, category_id);
            statement.setInt(3, supplier_id);
            if(image != null){
                statement.setBinaryStream(4, image,image.available());
            }
            else{
                 statement.setBinaryStream(4, null);
            }
            statement.setDouble(5, kdv_rate);
            statement.setDouble(6, price);
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
}
