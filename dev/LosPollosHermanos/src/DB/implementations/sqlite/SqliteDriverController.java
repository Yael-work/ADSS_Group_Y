package DB.implementations.sqlite;

import DB.util.HibernateUtil;
import DB.interfaces.IDriverController;
import Models.DBModels.transport.Driver;
import org.hibernate.Session;

import java.util.ArrayList;

public class SqliteDriverController implements IDriverController {
    private static SqliteDriverController instance;

    public static SqliteDriverController getInstance() {
        if (instance == null) {
            instance = new SqliteDriverController();
        }
        return instance;
    }

    @Override
    public int addDriver(Driver driver) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(driver);
            session.getTransaction().commit();
            return driver.getId();
        }
    }

    @Override
    public boolean updateDriver(Driver driver, int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Driver existing = session.get(Driver.class, id);
            if (existing == null) return false;
            existing.copyFromOther(driver);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public boolean deleteDriver(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Driver driver = session.get(Driver.class, id);
            if (driver != null) {
                session.remove(driver);
                session.getTransaction().commit();
                return true;
            }
            return false;
        }
    }

    @Override
    public Driver getDriverById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Driver.class, id);
        }
    }

    @Override
    public Driver getDriverByFullName(String firstName, String lastName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Driver WHERE firstName = :fn AND lastName = :ln", Driver.class)
                    .setParameter("fn", firstName)
                    .setParameter("ln", lastName)
                    .uniqueResult();
        }
    }

    @Override
    public Driver getDriveByPersonalId(String personalId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Driver WHERE personalId = :pid", Driver.class)
                    .setParameter("pid", personalId)
                    .uniqueResult();
        }
    }

    @Override
    public Driver getDriverByPhoneNumber(String phoneNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Driver WHERE phoneNumber = :ph", Driver.class)
                    .setParameter("ph", phoneNumber)
                    .uniqueResult();
        }
    }

    @Override
    public ArrayList<Driver> getAllRecords() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM Driver", Driver.class).getResultList());
        }
    }
}

