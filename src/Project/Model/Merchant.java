package Project.Model;

import Project.Model.Database.Database;
import Project.Model.Interface.Table;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.UUID;

public class Merchant extends Table {

    public String valid = "";
    public String name = "";
    public String webLink = "";
    public String address = "";

    public Merchant() {
        super("merchants", null);
    }

    public Merchant(String id, String name, String webLink, String address, String valid) {
        super("merchants", UUID.fromString(id));
        this.name = name;
        this.webLink = webLink;
        this.address = address;
        this.valid = valid;
    }

    public Merchant(ResultSet rS) throws SQLException {
        this(rS.getString("id"), rS.getString("name"), rS.getString("weblink"), rS.getString("address"), rS.getString("valid"));
    }

    public static Merchant getId(String id) {
        try {
            ResultSet resultSet = new Merchant().getIdEntity(id);

            if(!resultSet.next())return null;

            return new Merchant(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Merchant getMerchantByName(String name, boolean like){
        try{
            ArrayList<Pair<String, String>> searchSQL = new ArrayList<>();
            searchSQL.add(new Pair<>("name", name));
            ResultSet resultSet = new Merchant().search(searchSQL, like, false);
            if(!resultSet.next()) return null;
            return new Merchant(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public String install() {
        return "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id TEXT NOT NULL, " +
                "valid TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "weblink TEXT NOT NULL, " +
                "address TEXT NOT NULL" +
                ");";
    }

    @Override
    public boolean insert() {
        try {
            id = UUID.randomUUID();
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getIdString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, valid));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, name));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, webLink));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, address));

            Database.executeSQL("INSERT INTO " + tableName + " VALUES ( ?, ?, ?, ?, ?);", preparedSQL);
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
        try {
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, valid));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, name));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, webLink));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, address));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getIdString()));

            Database.executeSQL("UPDATE " + tableName + " SET valid = ?, name = ?, weblink = ?, address = ? WHERE id = ?;", preparedSQL);
            preparedSQL.clear();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
