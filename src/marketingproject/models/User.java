package marketingproject.models;

public class User {

    public Integer ID;
    public String Name;
    public String Surname;
    public String Username;
    public String Email;
    public String Country;
    public String City;
    public String Address;

    public User() {
    }

    public User(Integer id) {
        this.ID = id;
    }

    @Override
    public String toString() {
        return this.Name + " " + this.Surname;
    }
}
