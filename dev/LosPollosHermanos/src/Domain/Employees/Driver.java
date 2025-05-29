package Domain.Employees;

import Auxiliary.Enums.Models.LicenseTypeEnum;
import Models.DBModels.BaseModel;
import Models.DBModels.Employees.ARole;
import org.hibernate.Session;

public class Driver extends ARole {

    public Driver(int id) {
        super(id, Driver.class.getName());
        this.roleName = "Driver";
        ;
    }

    @Override
    public boolean copyFromOther(BaseModel other, Session session) {
        return false; // Implement logic if needed
    }

}

