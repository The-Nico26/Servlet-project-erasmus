package Project.Model;

import Project.Model.Database.Database;
import Project.Model.Interface.Table;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.UUID;

public class Cart extends Table {
    public String user = "";
    public ArrayList<CartElement> cartElements = new ArrayList<>();
    public String status = "";

    public Cart() { super("carts", null); }

    public Cart(String id, String user, String status){
        super("carts", UUID.fromString(id));
        this.user = user;
        this.status = status;
        this.cartElements = CartElement.getElementBy("cart",id);
    }
    public Cart(ResultSet resultSet) throws SQLException{
        this(resultSet.getString("id"), resultSet.getString("id_user"), resultSet.getString("status"));
    }

    public static Cart getId(String id){
        try{
            ResultSet resultSet = new Cart().getIdEntity(id);
            if(!resultSet.next()) return null;
            return new Cart(resultSet);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Cart> getCartByUser(String id){
        ArrayList<Cart> carts = new ArrayList<>();
        try{
            ArrayList<Pair<String, String>> searchSQL = new ArrayList<>();
            searchSQL.add(new Pair<>("id_user", id));
            ResultSet resultSet = new Cart().search(searchSQL, false, false);
            while(resultSet.next())
                carts.add(new Cart(resultSet));
        }catch(SQLException e){
            e.printStackTrace();
        }
        return carts;
    }

    @Override
    public String install() {
        return "CREATE TABLE IF NOT EXISTS "+tableName+" (" +
                "id TEXT NOT NULL, " +
                "id_user TEXT NOT NULL, " +
                "status TEXT NOT NULL " +
                ");";
    }

    @Override
    public boolean insert() {
        try{
            id = UUID.randomUUID();

            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getIdString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, user));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, status));

            Database.executeSQL("INSERT INTO "+tableName+" VALUES (?, ?, ?);", preparedSQL);
            preparedSQL.clear();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update() {
        try{
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, user));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, status));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getIdString()));

            Database.executeSQL("UPDATE " + tableName + " SET id_user = ?, status = ? WHERE id = ?;", preparedSQL);
            preparedSQL.clear();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
