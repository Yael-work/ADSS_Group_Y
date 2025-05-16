package Models.DBModels.Employees;

import Models.DBModels.BaseModel;
import org.hibernate.Session;

public class Cashier extends ARole {

    public Cashier(int id) {
        super(id, Cashier.class.getName());
        this.roleName = "Cashier";
    }

    @Override
    public boolean copyFromOther(BaseModel other, Session session) {
        return false;
    }
}
