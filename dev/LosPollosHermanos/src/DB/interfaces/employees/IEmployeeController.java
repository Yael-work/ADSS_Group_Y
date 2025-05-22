package DB.interfaces.employees;

import DB.interfaces.transport.IController;
import Models.DBModels.Employees.Employee;

public interface IEmployeeController extends IController<Employee> {
    public int addEmployeeToDB(Employee employee);
    public boolean removeEmployeeByIdFromDB(String id);
    public Employee findEmployeeById(String id);
    }
}
