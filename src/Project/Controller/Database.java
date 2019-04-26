package Project.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.TimeZone;

public class Database {

    private Statement statement;
    private static Database instance = null;

    private Database(){
        try {
            String url = "jdbc:mysql://localhost:3306/WWWBase1?serverTimezone=" + TimeZone.getDefault().getID();
            String username = "test";
            String password = "test";
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection conn = DriverManager.getConnection(url, username, password);
            this.statement = conn.createStatement();
        }catch (Exception ignored){}
    }

    private static Database getInstance(){
        if(instance == null){
            Database.instance = new Database();
        }
        return Database.instance;
    }


}
