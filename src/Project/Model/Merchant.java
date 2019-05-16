package Project.Model;

import Project.Model.Interface.Table;

import java.util.HashMap;

public class Merchant extends Table {
    private String name;
    private String webLink;
    private String address;
    private HashMap<String, User> users;

    public Merchant(String name, String webLink, String address) {
        this.name = name;
        this.webLink = webLink;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

    public User getUser(String id){
        return users.getOrDefault(id, null);
    }
    public void addUser(User user){
        users.put(user.getIdString(), user);
    }
}
