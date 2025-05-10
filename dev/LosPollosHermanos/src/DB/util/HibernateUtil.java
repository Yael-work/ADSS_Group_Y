package DB.util;

import Auxiliary.GlobalConfig;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory = null;

    /**
     * Function to invalidate the current session factory.
     */
    public static void invalidateSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }

    /**
     * Function to build the session factory.
     *
     * @return SessionFactory
     */
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure(GlobalConfig.getDatabaseConfigUrl()).setProperty("hibernate.show_sql",
                    GlobalConfig.logSql()).buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Failed to create SessionFactory: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Function to get the session factory.
     *
     * @return SessionFactory
     */
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
