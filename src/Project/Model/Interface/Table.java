package Project.Model.Interface;

import java.util.UUID;

public abstract class Table implements ITable {
    protected UUID id;

    @Override
    public String getIdString() {
        return id.toString();
    }

}
