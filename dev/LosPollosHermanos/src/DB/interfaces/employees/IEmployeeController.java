package DB.interfaces.employees;

import Auxiliary.Enums.Domain.Status;
import DB.interfaces.transport.IController;
import Models.DBModels.Employees.Employee;

public interface IEmployeeController extends IController<Employee> {
    public Status addEmployeeToDB(Employee employee);
    public Status removeEmployeeByIdFromDB(String id);
    public Employee findEmployeeById(String id);
    }
}
