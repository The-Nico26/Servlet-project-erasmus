package Project.Model;

import Project.Model.Database.Database;
import Project.Model.Interface.Table;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.UUID;

public class Collection extends Table {
    public ArrayList<Product> products;
    public String merchant;
    public String name;
    public String description;
    public Collection(){super("collections", null);}

    public Collection(String id, String name, String merchant, String description) {
        super("collections", UUID.fromString(id));
        this.products = Product.getProductsByCollection(id);
        this.name = name;
        this.description = description;
        this.merchant = merchant;
    }

    public Collection(ResultSet resultSet) throws SQLException {
        this(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("id_merchant"), resultSet.getString("description"));
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
    public static Collection getId(String id){
        try {
            ResultSet resultSet = new Collection().getIdEntity(id);
            if(!resultSet.next())return null;
            return new Collection(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Collection> getAllCollectionsByMerchant(String idMerchant){
        ArrayList<Collection> collections = new ArrayList<>();
        try {
            ArrayList<Pair<String, String>> searchSQL = new ArrayList<>();
            searchSQL.add(new Pair<>("id_merchant", idMerchant));
            ResultSet rS = new Collection().search(searchSQL, false, false);
            while (rS.next())
                collections.add(new Collection(rS));

        }catch (SQLException e){
            e.printStackTrace();
        }

        return collections;
    }

    @Override
    public boolean insert() {
        try {
            id = UUID.randomUUID();
            if(merchant.equals(""))return false;

            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getIdString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, merchant));
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
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, merchant));
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
