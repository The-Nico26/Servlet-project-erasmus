package Project.Model.Interface;

import java.util.UUID;

public interface ITable {
    String install();
    String getIdString();
    boolean insert();
    boolean update();
    boolean delete();
    boolean delete(UUID id);
}
