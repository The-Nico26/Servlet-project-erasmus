package Project.Model;

import Project.Model.Interface.Table;

public class TypeProduct extends Table {
    private String name;

    public TypeProduct(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
