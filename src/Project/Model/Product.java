package Project.Model;

import Project.Model.Interface.Table;

import java.util.HashMap;
import java.util.UUID;

public class Product extends Table {
    public String name;
    public String description;
    public Collection collection;
    public float price;
    public HashMap<String, OptionProduct> options;
    public TypeProduct typeProduct;

    public Product(){super("products", null);}

    public Product(String id, String name, String description, Collection collection, float price, HashMap<String, OptionProduct> options, TypeProduct typeProduct) {
        super("products", UUID.fromString(id));
        this.name = name;
        this.description = description;
        this.collection = collection;
        this.price = price;
        this.options = options;
        this.typeProduct = typeProduct;
    }

    @Override
    public String install() {
        return "CREATE TABLE IF NOT EXISTS products (" +
                "id TEXT NOT NULL, " +
                "id_collection INT NOT NULL, " +
                "id_typeproduct INT NOT NULL, " +
                "id_options TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "description TEXT NOT NULL, " +
                "price FLOAT NOT NULL" +
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
