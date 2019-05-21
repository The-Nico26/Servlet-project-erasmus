package Project.Model;

import Project.Model.Database.Database;
import Project.Model.Interface.Table;
import Project.Utils.Hachage;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class User extends Table {

    //INFO OBJECT
    public String name = "";
    public String email = "";
    private String password = "";
    public String address = "";
    public ArrayList<String> role = new ArrayList<>();
    public ArrayList<String> power = new ArrayList<>();
    public Merchant merchant = null;
    public User(){super("users", null);}

    public User(String id, String name, String email, String password, String address, String[] role, String[] power, Merchant merchant) {
        super("users", UUID.fromString(id));
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role.addAll(Arrays.asList(role));
        this.power.addAll(Arrays.asList(power));
        this.merchant = merchant;
    }

    public User(ResultSet rS) throws SQLException {
        this(rS.getString("id"), rS.getString("name"), rS.getString("email"), rS.getString("password"), rS.getString("address"), rS.getString("role").split(";"), rS.getString("power").split(";"), Merchant.getId(rS.getString("id_merchant")));
    }

    public static ArrayList<User> getAll(){
        ArrayList<User> users = new ArrayList<>();
        try{
            ResultSet rS = new User().getAllEntities();
            while(rS.next()){
                users.add(new User(rS));
            }
        }catch (Exception ignored){}

        return users;
    }

    public static User getId(String id){
        try{
            ResultSet rS = new User().getIdEntity(id);
            if(!rS.next())return null;

            return new User(rS);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return new User();
        }
    }

    public void setPassword(String password){
        this.password = Hachage.encrypt("SHA-512", password);
    }

    public static User getUserByEmail(String email){
        searchSQL.clear();
        searchSQL.add(new Pair<>("email", email));
        ResultSet rS = new User().search(searchSQL, false, false);
        if(rS == null) return null;
        try{
            rS.next();
            User us = new User(rS);
            return rS.next() ? null : us;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean checkPassword(String password){
        System.out.println(Hachage.encrypt("SHA-512", password));
        return this.password.equals(Hachage.encrypt("SHA-512", password));
    }

    public String getRoleString(){
        StringBuilder roleString = new StringBuilder();
        for (String r : role) {
            roleString.append(r).append(";");
        }
        return roleString.toString();
    }

    public String getPowerString(){
        StringBuilder powerString = new StringBuilder();
        for (String p : power) {
            powerString.append(p).append(";");
        }
        return powerString.toString();
    }


    @Override
    public String install() {
        return "CREATE TABLE IF NOT EXISTS "+tableName+" ( " +
            "id TEXT NOT NULL, " +
            "name VARCHAR(200) NOT NULL, " +
            "email TEXT NOT NULL, " +
            "password TEXT NOT NULL, " +
            "address TEXT NOT NULL, " +
            "role TEXT NOT NULL, " +
            "power TEXT NOT NULL, " +
            "id_merchant TEXT NOT NULL" +
            ");";
    }

    @Override
    public boolean insert() {
        id = UUID.randomUUID();
        try {
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getIdString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, name));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, email));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, password));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, address));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getRoleString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getPowerString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, (merchant != null?merchant.getIdString():"")));
            Database.executeSQL("INSERT INTO " + tableName + " VALUES (?, ?, ?, ?, ?, ?, ?, ?);", preparedSQL);
            preparedSQL.clear();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update() {
        if(id == null)return false;
        try{
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, name));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, email));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, password));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, address));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getRoleString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getPowerString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, (merchant != null?merchant.getIdString():"")));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getIdString()));
            Database.executeSQL("UPDATE "+tableName + " SET name = ?, email = ?, password = ?, address = ?, role = ?, power = ?, id_merchant = ? WHERE id = ?;", preparedSQL);
            preparedSQL.clear();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
