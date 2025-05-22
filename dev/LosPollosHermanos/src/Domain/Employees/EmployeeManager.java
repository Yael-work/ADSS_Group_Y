package Domain.Employees;


import Auxiliary.Enums.Domain.Status;
import Auxiliary.Helper;
import DB.DBManager;
import DB.WorkersDB.EmployeesDB;
import DB.interfaces.employees.IEmployeeController;
import Models.DBModels.Employees.ARole;
import Models.DBModels.Employees.Employee;
import Models.DBModels.transport.Branch;

import java.util.List;

public class EmployeeManager {

    private static final IEmployeeController employeeController = DBManager.getInstance().getEmployeeController();

    public static Status addEmployee(String id) {

        if (doesEmployeeExist(id)) {
            Helper.showError("Employee with this id already exists");
            return Status.Failure;
        }
        Branch b = new Employee(, name, address, phone);
        return branchController.addBranch(b) != Helper.UndefinedId;
    }

    private static boolean doesEmployeeExist(String id) {
        Employee emp = employeeController.findEmployeeById(id);
        if (emp != null)
            return true;
        return false;
    }


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
