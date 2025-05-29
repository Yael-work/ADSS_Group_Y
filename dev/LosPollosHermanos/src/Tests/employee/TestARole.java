package Tests.employee;

import Models.DBModels.Employees.ARole;
import Domain.Employees.Cashier;
import DB.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TestARole {
    public static void main(String[] args) {
        System.out.println("Testing ARole persistence...");

        try {
            // Test creating and persisting role entities
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            // Create role instances
            ARole cashier = new Cashier(0);

            System.out.println("Created role instance:");
            System.out.println("Cashier: " + cashier.getClass().getName());

            // Try to persist it
            session.merge(cashier);
            System.out.println("✅ Cashier persisted successfully");

            transaction.commit();
            session.close();

            System.out.println("🎉 ARole entity persisted successfully!");

        } catch (Exception e) {
            System.err.println("❌ Error testing ARole persistence:");
            e.printStackTrace();
        }
    }
}
