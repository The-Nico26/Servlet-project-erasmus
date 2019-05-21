package Project.Model;

import Project.Model.Database.Database;
import Project.Model.Interface.Table;
import javafx.util.Pair;

import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

public class Collection extends Table {
    public Merchant merchant;
    public String name;
    public String description;
    public Collection(){super("collections", null);}

    public Collection(String id, Merchant merchant, String name, String description) {
        super("collections", UUID.fromString(id));
        this.merchant = merchant;
        this.name = name;
        this.description = description;
    }

    @Override
    public String install() {
        return "CREATE TABLE IF NOT EXISTS "+tableName+" (" +
                "id TEXT NOT NULL, " +
                "id_merchant TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "description TEXT NOT NULL"+
                ");";
    }

    @Override
    public boolean insert() {
        try {
            id = UUID.randomUUID();
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getIdString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, merchant.getIdString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, name));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, description));

            Database.executeSQL("INSERT INTO " + tableName + " VALUES ( ?, ?, ?, ?);", preparedSQL);
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
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, merchant.getIdString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, description));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getIdString()));
            Database.executeSQL("UPDATE "+tableName + " SET name = ?, id_merchant = ?, description = ? WHERE id = ?;", preparedSQL);
            preparedSQL.clear();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
