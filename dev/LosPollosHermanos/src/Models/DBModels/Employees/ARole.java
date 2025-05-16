package Models.DBModels.Employees;

import Models.DBModels.BaseModel;

/**
 * Abstract class representing a role in the system.
 * This class is used to define different roles that employees can have.
 */
public abstract class ARole extends BaseModel {
    protected String roleName;

    public ARole(int id, String roleName) {
        super(id, ARole.class.getName());
        this.roleName = roleName;
    }

    public String getRole() {
        return roleName;
    }

}