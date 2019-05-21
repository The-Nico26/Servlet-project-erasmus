package Project.Model;


public class Model {
    public User user;

    public Model(String id){
        if(id != null)
        user = User.getId(id);
    }
}
