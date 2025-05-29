package Tests.employee;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TestSimpleHibernate {
    public static void main(String[] args) {
        System.out.println("Testing minimal Hibernate configuration...");
        
        try {
            // Create a minimal configuration without the problematic entities
            Configuration configuration = new Configuration();
            
            // Basic database settings
            configuration.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
            configuration.setProperty("hibernate.connection.url", "jdbc:sqlite:test.db");
            configuration.setProperty("dialect", "org.hibernate.community.dialect.SQLiteDialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            
            // Disable all bytecode enhancement
            configuration.setProperty("hibernate.bytecode.use_reflection_optimizer", "false");
            configuration.setProperty("hibernate.bytecode.provider", "none");
            configuration.setProperty("hibernate.show_sql", "false");
            configuration.setProperty("hibernate.format_sql", "false");
            configuration.setProperty("hibernate.generate_statistics", "false");
            configuration.setProperty("hibernate.use_sql_comments", "false");
            
            // Add only simple entities (not the problematic ARole hierarchy)
            configuration.addAnnotatedClass(Models.DBModels.Employees.BankAccount.class);
            configuration.addAnnotatedClass(Models.DBModels.Employees.Contract.class);
            configuration.addAnnotatedClass(Models.DBModels.Employees.Availability.class);
            
            // Try to build SessionFactory
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            
            if (sessionFactory != null && !sessionFactory.isClosed()) {
                System.out.println("✅ SUCCESS: Basic Hibernate SessionFactory created successfully!");
                sessionFactory.close();
                System.out.println("✅ SUCCESS: SessionFactory closed cleanly.");
                
                // Now try with ARole
                System.out.println("\nTesting with ARole hierarchy...");
                testWithARole();
                
            } else {
                System.out.println("❌ FAILED: SessionFactory is null or closed");
            }
            
        } catch (Exception e) {
            System.out.println("❌ FAILED: Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Test completed.");
    }
    
    private static void testWithARole() {
        try {
            Configuration configuration = new Configuration();
            
            // Basic database settings
            configuration.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
            configuration.setProperty("hibernate.connection.url", "jdbc:sqlite:test2.db");
            configuration.setProperty("dialect", "org.hibernate.community.dialect.SQLiteDialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            
            // Disable all bytecode enhancement
            configuration.setProperty("hibernate.bytecode.use_reflection_optimizer", "false");
            configuration.setProperty("hibernate.bytecode.provider", "none");
            configuration.setProperty("hibernate.show_sql", "false");
            configuration.setProperty("hibernate.format_sql", "false");
            configuration.setProperty("hibernate.generate_statistics", "false");
            configuration.setProperty("hibernate.use_sql_comments", "false");
            
            // Add the problematic entities
            configuration.addAnnotatedClass(Models.DBModels.Employees.ARole.class);
            configuration.addAnnotatedClass(Domain.Employees.Cashier.class);
            
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            
            if (sessionFactory != null && !sessionFactory.isClosed()) {
                System.out.println("✅ SUCCESS: ARole hierarchy works!");
                sessionFactory.close();
            } else {
                System.out.println("❌ FAILED: ARole SessionFactory is null or closed");
            }
            
        } catch (Exception e) {
            System.out.println("❌ FAILED: ARole test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
