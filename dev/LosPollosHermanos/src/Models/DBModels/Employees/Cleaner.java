package Models.DBModels.Employees;

import Models.DBModels.BaseModel;
import org.hibernate.Session;

public class Cleaner extends ARole{
    public Cleaner(int id) {
        super(id, Cleaner.class.getName());
        this.roleName = "Cleaner";
    }

    @Override
    public boolean copyFromOther(BaseModel other, Session session) {
        return false;
    }
}
