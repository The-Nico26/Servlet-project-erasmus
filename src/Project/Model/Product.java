package Project.Model;

import Project.Model.Database.Database;
import Project.Model.Interface.Table;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Product extends Table {
    public String name = "";
    public String description = "";
    public String collection = "";
    public float price = 0;
    public ArrayList<String[]> options = new ArrayList<>(); //[0] : Name [1] : Values
    public TypeProduct typeProduct;

    public Product(){super("products", null);}

    public Product(String id, String name, String description, String collection, float price, String options, TypeProduct typeProduct) {
        super("products", UUID.fromString(id));
        this.name = name;
        this.description = description;
        this.collection = collection;
        this.price = price;
        this.options = setOptionsString(options);
        this.typeProduct = typeProduct;
    }
    public Product(ResultSet resultSet) throws SQLException {
        this(resultSet.getString("id"), resultSet.getString("name"),
                resultSet.getString("description"), resultSet.getString("id_collection"),
                resultSet.getFloat("price"), resultSet.getString("options"), TypeProduct.getId(resultSet.getString("id_typeproduct")));
    }

    public static Product getId(String product) {
        try{
            ResultSet resultSet = new Product().getIdEntity(product);
            if(!resultSet.next())return null;
            return new Product(resultSet);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private String getOptionsString(){
        StringBuilder optionsString = new StringBuilder();
        for(String[] s : options){
            for(String ss : s){
                optionsString.append(ss).append(Character.toChars('\u0FD5'));
            }
            optionsString.append(Character.toChars('\u0FD4'));
        }
        return optionsString.toString();
    }

    private ArrayList<String[]> setOptionsString(String optionsString){
        ArrayList<String[]> optionsLocal = new ArrayList<>();
        for(String s : optionsString.split(Character.toString('\u0FD4'))){
            optionsLocal.add(s.split(Character.toString('\u0FD5')));
        }
        return optionsLocal;
    }

    public void addOption(String name, String value){
        options.add(new String[]{name, value});
    }

    static ArrayList<Product> getProductsByCollection(String collection){
        ArrayList<Product> products = new ArrayList<>();
        try{
            ArrayList<Pair<String, String>> searchSQL = new ArrayList<>();
            searchSQL.add(new Pair<>("id_collection", collection));
            ResultSet resultSet = new Product().search(searchSQL, false, false);
            while(resultSet.next())
                products.add(new Product(resultSet));

        }catch(SQLException e){
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public String install() {
        return "CREATE TABLE IF NOT EXISTS products (" +
                "id TEXT NOT NULL, " +
                "id_collection INT NOT NULL, " +
                "id_typeproduct INT NOT NULL, " +
                "options TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "description TEXT NOT NULL, " +
                "price FLOAT NOT NULL" +
                ");";
    }

    @Override
    public boolean insert() {
        try {
            id = UUID.randomUUID();
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getIdString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, collection));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, typeProduct.getIdString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getOptionsString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, name));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, description));
            preparedSQL.add(new Pair<>(Types.FLOAT, price));

            Database.executeSQL("INSERT INTO " + tableName + " VALUES ( ?, ?, ?, ?, ?, ?, ?);", preparedSQL);
            preparedSQL.clear();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update() {
        try{
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, collection));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, typeProduct.getIdString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getOptionsString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, name));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, description));
            preparedSQL.add(new Pair<>(Types.FLOAT, price));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getIdString()));

            Database.executeSQL("UPDATE " + tableName + " SET id_collection = ?, id_typeproduct = ?, options = ?, name = ?, description = ?, price = ? WHERE id = ?", preparedSQL);
            preparedSQL.clear();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
    }
}
