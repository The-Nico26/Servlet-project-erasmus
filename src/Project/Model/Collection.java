package Project.Model;

import Project.Model.Interface.Table;

public class Collection extends Table {
    private Merchant merchant;
    private String name;
    private String description;

    public Collection(Merchant merchant, String name, String description) {
        this.merchant = merchant;
        this.name = name;
        this.description = description;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
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
}
