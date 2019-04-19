package marketingproject.models;

public class Employee {

    public Integer ID;
    public String Name;
    public String Surname;
    public String Username;
    public String Email;

    public Employee() {
    }

    public Employee(Integer id) {
        this.ID = id;
    }

    @Override
    public String toString() {
        return this.Name + " " + this.Surname;
    }
}
