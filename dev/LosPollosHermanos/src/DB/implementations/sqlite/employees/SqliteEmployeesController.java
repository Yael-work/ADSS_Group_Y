package DB.implementations.sqlite.employees;
import Auxiliary.Enums.Domain.Status;
import Auxiliary.Helper;
import DB.interfaces.employees.IEmployeeController;
import DB.util.HibernateUtil;
import Models.DBModels.Employees.Employee;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class SqliteEmployeesController implements IEmployeeController {
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
            // Use merge instead of persist to handle detached entities
            session.merge(employee);
            session.getTransaction().commit();
            return Status.Success;
        } catch (Exception e) {
            Helper.showError("Failed to add Employee", e);
            return Status.Failure;
        }
    }

    public Status updateEmployee(Employee updatedEmployee) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(updatedEmployee);
            session.getTransaction().commit();
            return Status.Success;
        } catch (Exception e) {
            Helper.showError("Failed to update Employee", e);
            return Status.Failure;
        }
    }


    // This method is used to remove an employee by their ID
    public Status removeEmployeeFromDB(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            // Use HQL query to find employee by employeeID field, not primary key
            Employee employee = session.createQuery("FROM Employee WHERE employeeID = :empId", Employee.class)
                    .setParameter("empId", id)
                    .uniqueResult();
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
    public Employee findEmployeeById(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Use HQL query to find employee by employeeID field, not primary key
            return session.createQuery("FROM Employee WHERE employeeID = :empId", Employee.class)
                    .setParameter("empId", id)
                    .uniqueResult();
        } catch (Exception e) {
            Helper.showError("Failed to get employee by ID", e);
            return null;
        }
    }

    @Override
    public List<String> getAllIds() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<String> ids = session.createQuery("SELECT employeeID FROM Employee", String.class).getResultList();
            return ids;
        } catch (Exception e) {
            Helper.showError("Failed to get all employees IDs", e);
            return new ArrayList<>();
        }
    }

}
