package Models.DBModels.Employees;


import DB.WorkersDB.ShiftsDB;
import Models.DBModels.BaseModel;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class Shift_Manager extends ARole {
    public Shift_Manager(int id) {
        super(id, Shift_Manager.class.getName());
        this.roleName = "Shift Manager";
    }

    public static Status createShift(int id, LocalDate shiftDate, String shiftType,
                                     Employee shiftManager, List<Employee> assignedEmployees,
                                     List<Employee> allAvailableEmployees){
        Shift newShift = new Shift(id, shiftDate, shiftType, shiftManager, assignedEmployees, allAvailableEmployees);
        ShiftsDB.addUpcomingShift(newShift);
        return Status.Success;
    }

    public static Status removeEmployeeFromShift(Employee employeeToRemove , Shift shift) {
        shift.removeAssignedEmployee(employeeToRemove);
        return Status.Success;
    }

    public static Status addEmployeeToShift(Employee employeeToAdd , Shift shift) {
        shift.addAssignedEmployee(employeeToAdd);
        return Status.Success;
    }


    public static Status promote_Employee(Employee employeeToPromote, ARole newRole) {
        // Check if the employee already has the new role
        for (ARole role : employeeToPromote.getRolesList()) {
            if (role.getRole().equals(newRole.getRole())) {
                System.out.println("Employee already has this role.");
                return Status.Failure;
            }
        }
        employeeToPromote.addNewRoleToList(newRole);
        return Status.Success;
    }

    @Override
    public boolean copyFromOther(BaseModel other, Session session) {
        return false;
    }
}





