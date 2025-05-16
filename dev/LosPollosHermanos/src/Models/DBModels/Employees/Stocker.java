package Models.DBModels.Employees;

import Models.DBModels.BaseModel;
import org.hibernate.Session;

public class Stocker extends ARole {
    public Stocker(int id) {
        super(id, Stocker.class.getName());
        this.roleName = "Stocker";
    }

    @Override
    public boolean copyFromOther(BaseModel other, Session session) {
        return false;
    }
}
