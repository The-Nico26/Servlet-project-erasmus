package Project.Model;

import Project.Model.Database.Database;
import Project.Model.Interface.Table;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.UUID;

public class CartElement extends Table {
    public String merchant;
    public String cart;
    public String product;

    public String productName;
    public float productPrice;
    public int productNumber;

    public CartElement() { super("cart_elements", null); }

    public CartElement(String id, String merchant, String product, String productName, float productPrice, int productNumber, String cart) {
        super("cart_elements", UUID.fromString(id));
        this.merchant = merchant;
        this.product = product;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productNumber = productNumber;
        this.cart = cart;
    }
    public CartElement(ResultSet resultSet) throws SQLException {
        this(resultSet.getString("id"), resultSet.getString("id_merchant"), resultSet.getString("id_product"), resultSet.getString("product"), resultSet.getFloat("product_price"), resultSet.getInt("product_number"), resultSet.getString("id_cart"));
    }
    @Override
    public String install() {
        return "CREATE TABLE IF NOT EXISTS "+tableName+" (" +
                "id TEXT NOT NULL, " +
                "id_merchant TEXT NOT NULL, " +
                "id_product TEXT NOT NULL, " +
                "id_cart TEXT NOT NULL, " +
                "product TEXT NOT NULL, " +
                "product_price FLOAT NOT NULL, " +
                "product_number INTEGER NOT NULL " +
                ");";
    }
    public static CartElement getID(String id){
        try{
            ResultSet resultSet = new CartElement().getIdEntity(id);
            if(!resultSet.next())return null;
            return new CartElement(resultSet);

        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<CartElement> getElementBy(String by, String id){
        ArrayList<CartElement> cartElements = new ArrayList<>();
        try{
            ArrayList<Pair<String, String>> searchSQL = new ArrayList<>();
            searchSQL.add(new Pair<>("id_"+by, id));
            ResultSet resultSet = new CartElement().search(searchSQL, false, false);
            while(resultSet.next())
                cartElements.add(new CartElement(resultSet));

        }catch(SQLException e){
            e.printStackTrace();
        }
        return cartElements;
    }

    @Override
    public boolean insert() {
        try{
            id = UUID.randomUUID();

            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getIdString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, merchant));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, product));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, cart));

            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, productName));
            preparedSQL.add(new Pair<>(Types.FLOAT, productPrice));
            preparedSQL.add(new Pair<>(Types.INTEGER, productNumber));
            Database.executeSQL("INSERT INTO "+tableName+" VALUES (?, ?, ?, ?, ?, ?, ?)", preparedSQL);
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
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, merchant));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, product));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, cart));

            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, productName));
            preparedSQL.add(new Pair<>(Types.FLOAT, productPrice));
            preparedSQL.add(new Pair<>(Types.INTEGER, productNumber));

            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getIdString()));

            Database.executeSQL("UPDATE "+tableName+" SET id_merchant = ?, id_product = ?, id_cart = ?, product = ?, product_price = ?, product_number = ? WHERE id = ?",preparedSQL);
            preparedSQL.clear();
            return true;
        }catch (SQLException e){
            return false;
        }
    }
}
