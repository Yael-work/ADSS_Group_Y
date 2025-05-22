package Domain.Employees;


import DB.WorkersDB.EmployeesDB;
import Models.DBModels.Employees.ARole;
import Models.DBModels.Employees.Employee;

import java.util.List;

public class EmployeeManager {
    //TODO: understand this line
    //private static final IDriverController driverController = DBManager.getInstance().getDriverController();

    public static void addNewRoleToList(ARole newRole , Employee employee) {
        for (int i = 0; i < employee.getAllRoles().size(); i++) {
            if (employee.getRolesList().get(i).getRole().equals(newRole.getRole())) {
                return;
            }
        }
        List<ARole> updatedRoles = employee.getAllRoles();
        updatedRoles.add(newRole);
        employee.setAllRoles(updatedRoles);
    }

    public static List<Employee> getAllEmployees() {
        return EmployeesDB.getMockEmployeeDB();
    }

    public static void use_cancel_card() {
        System.out.println("Cancel card used");
    }

}
