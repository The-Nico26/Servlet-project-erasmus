package Project.Model.Database;

import Project.Model.Interface.ITable;
import Project.Model.Interface.Table;
import Project.Model.*;
import javafx.util.Pair;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private ArrayList<Table> elements = new ArrayList<>();

    private Statement statement;
    private static Database instance = null;
    private String url = "jdbc:sqlite:database.db";

    private Database(){
        initElement();
        try {
            if(!(new File("database.db").exists())) createDatabase();
            Class.forName("org.sqlite.JDBC");
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

    public static ResultSet rowsSQL(String sql, ArrayList<Pair<Integer, String>> hashMap) throws SQLException {
        Database db = getInstance();
        PreparedStatement sqlStatement = db.statement.getConnection().prepareStatement(sql);
        int index = 1;

        for (Pair<Integer, String> has : hashMap) {
            sqlStatement.setObject(index, has.getValue(), has.getKey());
            index++;
        }
        return sqlStatement.executeQuery();
    }

    public static int executeSQL(String sql, ArrayList<Pair<Integer, String>> hashMap) throws SQLException {
        Database db = getInstance();
        PreparedStatement sqlStatement = db.statement.getConnection().prepareStatement(sql);
        int index = 1;
        for (Pair<Integer, String> has : hashMap) {
            sqlStatement.setObject(index, has.getValue(), has.getKey());
            index++;
        }
        return sqlStatement.executeUpdate();
    }

    private void createDatabase(){
        try {
            Class.forName("org.sqlite.JDBC");
            try(Connection conn  = DriverManager.getConnection(url)){
                if(conn != null){
                    Statement stmt = conn.createStatement();
                    for (ITable element : elements)
                        stmt.execute(element.install());
                }else{
                    System.err.println("ERROR - CREATED DATABASE");
                    System.exit(1);
                }
                System.out.println("Database CREATED");
            }catch (SQLException e){
                System.out.println("________---______________---_________________________---_____________");
                System.err.println(e.getMessage());
                System.out.println("________---______________---_________________________---_____________");
                System.exit(1);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void initElement(){
        elements.add(new User());
        elements.add(new Collection());
        elements.add(new Merchant());
        elements.add(new OptionProduct());
        elements.add(new Product());
        elements.add(new TypeProduct());
    }
}
