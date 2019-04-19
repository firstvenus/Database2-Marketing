package marketingproject;

import marketingproject.models.Employee;
import marketingproject.models.User;

public class DataStore {
    private static DataStore single_instance = null;

    private Employee employee;
    private User user;

    private DataStore() {

    }

    public static DataStore getInstance() {
        if (single_instance == null) {
            single_instance = new DataStore();
        }

        return single_instance;
    }

    public void setEmployee(Employee emp){
        this.employee = emp;
    }
    
    public void setUser(User user){
        this.user = user;
    }
    
    public Employee getEmployee(){
        return employee;
    }
    
    public User getUser(){
        return user;
    }
}
