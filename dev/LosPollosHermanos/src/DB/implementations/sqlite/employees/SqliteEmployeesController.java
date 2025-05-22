package DB.implementations.sqlite.employees;

import Auxiliary.Enums.Domain.Status;
import Auxiliary.Helper;
import DB.implementations.sqlite.transport.SqliteBranchController;
import DB.util.HibernateUtil;
import Models.DBModels.Employees.Employee;
import Models.DBModels.transport.Branch;
import org.hibernate.Session;

public class SqliteEmployeesController {
    private static SqliteEmployeesController instance;

    public static SqliteEmployeesController getInstance() {
        if (instance == null) {
            instance = new SqliteEmployeesController();
        }
        return instance;
    }

    public Status addEmployeeToDB(Employee employee) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(employee);
            session.getTransaction().commit();
            return Status.Success;
        } catch (Exception e) {
            Helper.showError("Failed to add Employee", e);
            return Status.Failure;
        }
    }


    // This method is used to remove an employee by their ID
    public Status removeEmployeeByIdFromDB(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                session.remove(employee);
                session.getTransaction().commit();
                return Status.Success;
            }
            return Status.Failure;
        } catch (Exception e) {
            Helper.showError("Failed to delete Employee", e);
            return Status.Failure;
        }
    }

    // This method is used to find an employee by their ID
    public static Employee findEmployeeById(String id) {
        for (Employee employee : mockEmployeeDB) {
            if (employee.getEmployeeID().trim().equals(id.trim())) {
                return employee;
            }
        }
        return null;
    }
}
