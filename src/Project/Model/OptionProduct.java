package Project.Model;

import Project.Model.Interface.Table;

import java.util.ArrayList;
import java.util.UUID;

public class OptionProduct extends Table {
    public String name;
    public ArrayList<String> options;
    public OptionProduct(){super("product_options", null);}
    public OptionProduct(String id, String name, ArrayList<String> options) {
        super("product_options", UUID.fromString(id));
        this.name = name;
        this.options = options;
    }

    @Override
    public String install() {
        return "CREATE TABLE IF NOT EXISTS "+tableName+" (" +
                "id TEXT NOT NULL, " +
                "name VARCHAR(255) NOT NULL, " +
                "options TEXT NOT NULL" +
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
