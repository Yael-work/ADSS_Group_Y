package Domain.Employees;


import Auxiliary.Enums.Domain.Status;
import Auxiliary.Helper;
import DB.DBemployeeManager;
import DB.interfaces.employees.IEmployeeController;
import Models.DBModels.Employees.*;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManagerCLS {

    private static final IEmployeeController employeeController = DBemployeeManager.getInstance().getEmployeeController();

    public static Status addEmployee(Employee employee) {
        return employeeController.addEmployeeToDB(employee);
    }

    public static boolean doesEmployeeExist(String employeeID) {
        Employee emp = getEmployeeById(employeeID);
        if (emp != null)
            return true;
        return false;
    }

    public static Employee getEmployeeById(String employeeID) {
        Employee employee = employeeController.findEmployeeById(employeeID);
        if (employee == null) {
            Helper.showError(String.format("Cant get employee with id=%s, it does not exist", employeeID));
        }
        return employee;
    }

    public static Status deleteEmployee(String employeeID) {
        return employeeController.removeEmployeeFromDB(employeeID);
    }

    public static Status updateEmployee(Employee updatedEmployee) {
        return employeeController.updateEmployee(updatedEmployee);
    }


    public static Status addNewRoleToEmployee(ARole newRole , Employee employee) {
        for (int i = 0; i < employee.getAllRoles().size(); i++) {
            if (employee.getRolesList().get(i).getRole().equals(newRole.getRole())) {
                return Status.Failure;
            }
        }
        List<ARole> updatedRoles = employee.getAllRoles();
        updatedRoles.add(newRole);
        employee.setAllRoles(updatedRoles);
        return employeeController.updateEmployee(employee);
    }

    public static List<Employee> getAllEmployees() {{
        ArrayList<Employee> employeesList = new ArrayList<>();
        for (String id : employeeController.getAllIds()) {
            Employee employee = employeeController.findEmployeeById(id);
            if (employee == null) {
                Helper.showError(String.format("Cant get employee with id=%s, it does not exist", id));
            } else {
                employeesList.add(employee);
            }
        }
        return employeesList;
        }
    }

    public static void use_cancel_card() {
        Helper.println("Cancel card used");
    }

}
