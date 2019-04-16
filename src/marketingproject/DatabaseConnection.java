package marketingproject;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.SQLException;
//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
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
            System.err.println("Deneme");
            }catch(SQLException ex){
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean AdminLogin(String UserName,String Password){
        boolean result = false;
        String SQL = "SELECT * FROM employee WHERE employeeUserName = ? AND employeePassword = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(SQL,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            statement.setString(1,UserName);
            statement.setString(2,Password);
            ResultSet rsLocal = statement.executeQuery();
            rsLocal.absolute(0);
            if(rsLocal.next()){
              result = true;  
            }
            return result;
        }catch(SQLException ex){
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    } 
    
    public boolean UserLogin(String UserName,String Password){
        boolean result = false;
        String SQL = "SELECT * FROM user WHERE UserName = ? AND UserPassword = ?";
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
}
