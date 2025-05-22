package Domain.Employees;

import DB.WorkersDB.EmployeesDB;
import Models.DBModels.BaseModel;
import Models.DBModels.Employees.ARole;
import Models.DBModels.Employees.Employee;
import Models.DBModels.Employees.Shift;
import Auxiliary.Enums.Domain.Status;
import org.hibernate.Session;

import java.time.LocalTime;


public class HR_Lead extends ARole {
    public HR_Lead(int id) {
        super(id, Stocker.class.getName());
        this.roleName = "HR Lead";
    }

    public static Status Terminate_Employee_from_System(String employeeID)
    {
        EmployeesDB.removeEmployeeByIdFromDB(employeeID);
        return Status.Success;
    }

    public static Status Add_Employee_to_System(Employee employee)
    {
        EmployeesDB.addEmployeeToDB(employee);
        return Status.Success;
    }

    public static Status Set_Shift_Hours(String shiftType, LocalTime startHour, LocalTime endHour) {

        if (shiftType.equals("Day")) {
            Shift.setStartHourDay(startHour);
            Shift.setEndHourDay(endHour);
        }
        else {
            Shift.setStartHourNight(startHour);
            Shift.setEndHourNight(endHour);
        }
        return Status.Success;
    }

    public static void Update_Required_Role_to_Shift(int action ,int roleAmount, String roleName) {
        if (action == 1) {
            shiftsManager.addRequiredRoles(roleName, roleAmount);
        } else if (action == 2) {
            shiftsManager.removeRequiredRole(roleName);
        }
    }

    @Override
    public boolean copyFromOther(BaseModel other, Session session) {
        return false;
    }
}
