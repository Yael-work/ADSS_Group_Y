package DB.implementations.sqlite;

import Auxiliary.Helper;
import DB.interfaces.IDeliveryFileController;
import DB.util.HibernateUtil;
import Models.DBModels.transport.DeliveryFile;
import Models.DBModels.transport.DeliveryFileItem;

import java.util.ArrayList;
import java.util.List;

public class SqliteDeliveryFileController implements IDeliveryFileController {
    private static SqliteDeliveryFileController instance = null;

    private SqliteDeliveryFileController() {
    }

    public static SqliteDeliveryFileController getInstance() {
        if (instance == null) {
            instance = new SqliteDeliveryFileController();
        }
        return instance;
    }

    @Override
    public int addDeliveryFile(DeliveryFile deliveryFile) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(deliveryFile);
            session.getTransaction().commit();
            return deliveryFile.getId();
        } catch (Exception e) {
            Helper.showError("Failed to add DeliveryFile", e);
            return Helper.UndefinedId;
        }
    }

    @Override
    public boolean addItemsToDeliveryFile(int deliveryFileId, List<DeliveryFileItem> items) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            DeliveryFile deliveryFile = session.get(DeliveryFile.class, deliveryFileId);
            if (deliveryFile != null) {
                for (var item : items) {
                    item.setDeliveryFile(deliveryFile);
                    session.persist(item);
                }
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            Helper.showError("Failed to add items to DeliveryFile", e);
        }
        return false;
    }

    @Override
    public boolean removeItemsFromDeliveryFile(int deliveryFileId, List<DeliveryFileItem> items) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            DeliveryFile deliveryFile = session.get(DeliveryFile.class, deliveryFileId);
            if (deliveryFile != null) {
                for (var item : items) {
                    item.setDeliveryFile(deliveryFile);
                    session.remove(item);
                }
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            Helper.showError("Failed to remove items from DeliveryFile", e);
        }
        return false;
    }

    @Override
    public boolean deleteDeliveryFile(int id) {
        return false;
    }

    @Override
    public DeliveryFile getDeliveryFileById(int id) {
        return null;
    }

    @Override
    public ArrayList<DeliveryFile> getAllRecords() {
        return null;
    }
}
