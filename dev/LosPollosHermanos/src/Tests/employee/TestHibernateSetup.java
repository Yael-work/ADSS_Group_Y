package Tests.employee;

import DB.util.HibernateUtil;
import org.hibernate.SessionFactory;

public class TestHibernateSetup {
    public static void main(String[] args) {
        System.out.println("Testing Hibernate SessionFactory creation...");
        
        try {
            // Set test mode to use test database
            Auxiliary.GlobalConfig.setTestMode(true);
            
            // Try to create SessionFactory
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            
            if (sessionFactory != null && !sessionFactory.isClosed()) {
                System.out.println("✅ SUCCESS: Hibernate SessionFactory created successfully!");
                System.out.println("✅ SUCCESS: No bytecode enhancement errors!");
                
                // Close the session factory
                sessionFactory.close();
                System.out.println("✅ SUCCESS: SessionFactory closed cleanly.");
            } else {
                System.out.println("❌ FAILED: SessionFactory is null or closed");
            }
            
        } catch (Exception e) {
            System.out.println("❌ FAILED: Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Test completed.");
    }
}
