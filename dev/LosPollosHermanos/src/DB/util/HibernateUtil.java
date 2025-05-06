package DB.util;

import Auxiliary.GlobalConfig;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory = null;

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure(GlobalConfig.getDatabaseConfigUrl()).buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Failed to create SessionFactory: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            sessionFactory = buildSessionFactory();
        }
        if (sessionFactory.isClosed()) {
            System.err.println("Failed to create SessionFactory");
            throw new ExceptionInInitializerError();
        }
        return sessionFactory;
    }
}
