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
    public Employee findEmployeeById(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employee.class, id);
        } catch (Exception e) {
            Helper.showError("Failed to get employee by ID", e);
            return null;
        }
    }

    @Override
    public List<Integer> getAllIds() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT id FROM Employee", Integer.class).list();
        } catch (Exception e) {
            Helper.showError("Failed to get all employees IDs", e);
            return new ArrayList<>();
        }
    }

}
