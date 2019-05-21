package Project.Model.Interface;

import Project.Model.Database.Database;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.UUID;

public abstract class Table implements ITable {
    protected UUID id;
    protected String tableName;
    protected ArrayList<Pair<Integer, String>> preparedSQL;
    protected static ArrayList<Pair<String, String>> searchSQL = new ArrayList<>();

    protected Table(String table, UUID id){
        this.tableName = table;
        this.id = id;
        this.preparedSQL = new ArrayList<>();
    }
    @Override
    public String getIdString() {
        return id.toString();
    }

    public ResultSet getAllEntities() throws SQLException {
        return Database.rowsSQL("SELECT * FROM "+tableName, new ArrayList<>());
    }

    public ResultSet getIdEntity(String id) throws SQLException {
        preparedSQL.add(new Pair<>(Types.LONGVARCHAR, id));
        ResultSet r =  Database.rowsSQL("SELECT * FROM "+tableName+" WHERE id = ?", preparedSQL);
        preparedSQL.clear();
        return r;
    }

    protected ResultSet search(ArrayList<Pair<String, String>>  map, boolean like, boolean or){
        String sql = "SELECT * FROM "+tableName+" WHERE ";
        ArrayList<Pair<Integer, String>> values = new ArrayList<>();

        for (Pair<String, String> m :map){
            sql += m.getKey()+" "+(like?"LIKE":"=")+" ? "+(or?"OR ":"AND ");
            String v = m.getValue();
            if(like)
                v = "%"+v+"%";
            values.add(new Pair<>(Types.LONGNVARCHAR, v));
        }
        if(or) sql = sql.substring(0, sql.length()-3);
        else sql = sql.substring(0, sql.length()-4);
        sql += ";";
        try{ return Database.rowsSQL(sql, values); }catch (SQLException e){ e.printStackTrace(); return null;}
    }
    @Override
    public boolean delete(UUID id) {
        try{
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, id.toString()));
            Database.executeSQL("DELETE FROM "+tableName+" WHERE id = ?;", preparedSQL);
            preparedSQL.clear();
        }catch(SQLException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean delete() {
        return delete(id);
    }
}
