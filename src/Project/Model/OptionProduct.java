package Project.Model;

import Project.Model.Interface.Table;

import java.util.ArrayList;

public class OptionProduct extends Table {
    private String name;
    private ArrayList<String> options;

    public OptionProduct(String name, ArrayList<String> options) {
        this.name = name;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getOptions() {
        return options;
    }
}
