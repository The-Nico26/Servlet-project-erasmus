package Project.Model;

import Project.Model.Database.Database;
import Project.Model.Interface.Table;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.UUID;

public class TypeProduct extends Table {
    public String name;
    public TypeProduct(){super("product_types", null);}
    private TypeProduct(String name, String id) {
        super("product_types", UUID.fromString(id));
        this.name = name;
    }

    private TypeProduct(ResultSet resultSet) throws SQLException {
        this(resultSet.getString("name"), resultSet.getString("id"));
    }
    public static TypeProduct getId(String id_typeproduct) {
        try{
            ResultSet resultSet = new TypeProduct().getIdEntity(id_typeproduct);
            if(!resultSet.next())return null;
            return new TypeProduct(resultSet);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<TypeProduct> getAll(){
        ArrayList<TypeProduct> typeProducts = new ArrayList<>();
        try{
            ResultSet resultSet = new TypeProduct().getAllEntities();
            while(resultSet.next())
                typeProducts.add(new TypeProduct(resultSet));

        }catch(SQLException e){
            e.printStackTrace();
        }
        return typeProducts;
    }

    public String install() {
        return "CREATE TABLE "+tableName+" (" +
                "id TEXT NOT NULL, " +
                "name TEXT NOT NULL" +
                ");";
    }

    @Override
    public boolean insert() {
        try{
            id = UUID.randomUUID();
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getIdString()));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, name));
            Database.executeSQL("INSERT INTO "+tableName+" VALUES (?, ?)", preparedSQL);
            preparedSQL.clear();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update() {
        try{
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, name));
            preparedSQL.add(new Pair<>(Types.LONGVARCHAR, getIdString()));
            Database.executeSQL("UPDATE "+tableName+" SET name = ? WHERE id = ?", preparedSQL);
            preparedSQL.clear();
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
