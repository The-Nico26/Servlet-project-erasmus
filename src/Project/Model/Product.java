package Project.Model;

import Project.Model.Interface.Table;

import java.util.HashMap;

public class Product extends Table {
    private String name;
    private String description;
    private Collection collection;
    private float price;
    private HashMap<String, OptionProduct> options;
    private TypeProduct typeProduct;

    public Product(String name, String description, Collection collection, float price, HashMap<String, OptionProduct> options, TypeProduct typeProduct) {
        this.name = name;
        this.description = description;
        this.collection = collection;
        this.price = price;
        this.options = options;
        this.typeProduct = typeProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public HashMap<String, OptionProduct> getOptions() {
        return options;
    }

    public void setOptions(HashMap<String, OptionProduct> options) {
        this.options = options;
    }

    public TypeProduct getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(TypeProduct typeProduct) {
        this.typeProduct = typeProduct;
    }
}
