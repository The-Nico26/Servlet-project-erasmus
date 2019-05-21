package Project.Model;

import Project.Model.Interface.Table;

import java.util.UUID;

public class TypeProduct extends Table {
    public String name;
    public TypeProduct(){super("product_types", null);}
    public TypeProduct(String name, String id) {
        super("product_types", UUID.fromString(id));
        this.name = name;
    }

    public String install() {
        return "CREATE TABLE "+tableName+" (" +
                "id TEXT NOT NULL, " +
                "name TEXT NOT NULL" +
                ");";
    }

    @Override
    public boolean insert() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }
}
