package marketingproject;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DatabaseConnection {
    public static Connection connection;
    public DatabaseConnection() throws SQLException {
        try{
            MysqlDataSource datasource = new MysqlDataSource();
            datasource.setUser("root");
            datasource.setPassword("1234");
            datasource.setPort(3306);
            datasource.setDatabaseName("market");
            connection = datasource.getConnection();
            }catch(SQLException ex){
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean UserLogin(String UserName,String Password){
        boolean result = false;
        String SQL = "SELECT * FROM user WHERE Username = ? AND Password = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(SQL,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            statement.setString(1,UserName);
            statement.setString(2,Password);
            ResultSet rsLocal = statement.executeQuery();
            rsLocal.absolute(0);
            if(rsLocal.next()){
              result = true;  
            }
        }catch(SQLException ex){
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public boolean EmployeeLogin(String UserName,String Password){
        boolean result = false;
        String SQL = "SELECT * FROM employee WHERE Username = ? AND Password = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(SQL,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            statement.setString(1,UserName);
            statement.setString(2,Password);
            ResultSet rsLocal = statement.executeQuery();
            rsLocal.absolute(0);
            if(rsLocal.next()){
              result = true;  
            }
        }catch(SQLException ex){
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public boolean Signup(String firstname, String lastname,String username, String password, String email,String country,String city,String address,String phone){
        boolean result = false;
        String SQL = "CALL RegisterUser(?,?,?,?,?,?,?,?,?);";
        System.out.println("signup çalıştı 65 db");
        try{
            System.out.println("signup çalıştı 67 db");
            PreparedStatement statement = connection.prepareStatement(SQL,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            statement.setString(1,firstname);
            statement.setString(2,lastname);
            statement.setString(3,username);
            statement.setString(4,password);
            statement.setString(5,address);
            statement.setString(6,city);
            statement.setString(7,phone);
            statement.setString(8,country);
            statement.setString(9,email);
            
            statement.executeQuery();
            return true;
        }catch(SQLException ex){
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception ex){
        }
        return result;
    } 
}
