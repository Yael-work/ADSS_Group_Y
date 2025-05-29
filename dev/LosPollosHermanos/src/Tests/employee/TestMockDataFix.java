package Tests.employee;

import DB.implementations.MockFactory.MockEmployeeData;
import DB.implementations.MockFactory.MockShiftsData;
import DB.util.HibernateUtil;
import Models.DBModels.Employees.Employee;
import Models.DBModels.Employees.Shift;
import org.hibernate.Session;

import java.util.List;

public class TestMockDataFix {
    public static void main(String[] args) {
        System.out.println("Testing mock data fix...");
        
        try {
            // Set test mode
            Auxiliary.GlobalConfig.setTestMode(true);
            
            // Get session
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                System.out.println("✅ Database connection established");
                
                // Begin transaction for employees
                session.beginTransaction();
                
                // Create and add mock employees
                List<Employee> employees = MockEmployeeData.createMockEmployees();
                System.out.println("✅ Created " + employees.size() + " mock employees");
                
                for (Employee emp : employees) {
                    session.merge(emp);
                    System.out.println("✅ Added employee: " + emp.getEmployeeID());
                }
                
                // Commit employee transaction
                session.getTransaction().commit();
                System.out.println("✅ Employee transaction committed");
                
                // Begin new transaction for shifts
                session.beginTransaction();
                
                // Fetch persisted employees
                List<Employee> persistedEmployees = session.createQuery("FROM Employee", Employee.class).getResultList();
                System.out.println("✅ Fetched " + persistedEmployees.size() + " persisted employees");
                
                // Create shifts with persisted employees
                List<Shift> shifts = MockShiftsData.createUpcomingShiftsMockDataWithEmployees(persistedEmployees);
                System.out.println("✅ Created " + shifts.size() + " mock shifts");
                
                for (Shift shift : shifts) {
                    session.merge(shift);
                    System.out.println("✅ Added shift: " + shift.getShiftID());
                }
                
                // Commit shift transaction
                session.getTransaction().commit();
                System.out.println("✅ Shift transaction committed");
                
                System.out.println("🎉 SUCCESS: Mock data loading completed without errors!");
            }
            
        } catch (Exception e) {
            System.out.println("❌ FAILED: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Reset to production mode
            Auxiliary.GlobalConfig.setTestMode(false);
        }
        
        System.out.println("Test completed.");
    }
}
