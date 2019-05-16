package Project.Model;

import Project.Model.Interface.*;

public class User extends Table {

    //INFO TABLE
    private String nmTB = "users";

    //INFO OBJECT
    public String name;
    public String email;
    public String password;
    public String[] role;
    public String[] power;

    public User(String name, String email, String password, String[] role, String[] power) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.power = power;
    }

    @Override
    public boolean install() {
        String installSQL =
            "CREATE TABLE IF NOT EXITS " + nmTB + "(" +
            "id integer NOT NULL AUTO_INCREMENT, " +
            "name varchar(200) NOT NULL, " +
            "email text NOT NULL, " +
            "password text NOT NULL, " +
            "role text NOT NULL, " +
            "power text NOT NULL" +
            "PRIMARY KEY (id)" +
            ");";
        return true;
    }
}
