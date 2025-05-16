package Models.DBModels.Employees;

import Models.DBModels.BaseModel;
import org.hibernate.Session;

public class Security extends ARole{
    public Security(int id) {
        super(id, Stocker.class.getName());
        this.roleName = "Security";
    }

    @Override
    public boolean copyFromOther(BaseModel other, Session session) {
        return false;
    }
}
