package Project.Model.Database;

import Project.Model.Interface.Table;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.TimeZone;

public class Database {
    private ArrayList<Table> elements;

    private Statement statement;
    private static Database instance = null;
    String url = "jdbc:sqlite:database.db";

    private Database(){
        try {
            if(!(new File("database.db").exists())) createDatabase();
            Connection conn = DriverManager.getConnection(url);
            this.statement = conn.createStatement();
        }catch (Exception ignored){}
    }

    private static Database getInstance(){
        if(instance == null){
            Database.instance = new Database();
        }
        return Database.instance;
    }

    public static ResultSet rowsSQL(String sql) throws SQLException {
        return getInstance().statement.executeQuery(sql);
    }

    public static boolean executeSQL(String sql) throws SQLException {
        Database db = getInstance();
        return db.statement.execute(sql);
    }

    private void createDatabase(){
        try(Connection conn  = DriverManager.getConnection(url)){
            if(conn != null){
                for (Table element : elements)
                    if(!element.install()){
                        System.err.println(element.getClass()+" not installed");
                        System.exit(1);
                    }
            }else{
                System.err.println("ERROR - CREATED DATABASE");
                System.exit(1);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
