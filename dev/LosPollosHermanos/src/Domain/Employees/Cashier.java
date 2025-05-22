package Domain.Employees;

import Models.DBModels.BaseModel;
import Models.DBModels.Employees.ARole;
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
