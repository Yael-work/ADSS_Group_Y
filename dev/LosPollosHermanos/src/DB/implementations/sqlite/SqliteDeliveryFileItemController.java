package DB.implementations.sqlite;

import Auxiliary.Helper;
import DB.interfaces.IDeliveryFileItemController;
import DB.util.HibernateUtil;
import Models.DBModels.transport.DeliveryFileItem;

import java.util.ArrayList;
import java.util.List;

public class SqliteDeliveryFileItemController implements IDeliveryFileItemController {
    private static SqliteDeliveryFileItemController instance = null;

    /**
     * Returns the singleton instance of MockDeliveryFileItemController.
     *
     * @return The singleton instance of MockDeliveryFileItemController.
     */
    public static SqliteDeliveryFileItemController getInstance() {
        if (instance == null) {
            instance = new SqliteDeliveryFileItemController();
        }
        return instance;
    }


    @Override
    public int addDeliveryFileItem(DeliveryFileItem deliveryFileItem) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(deliveryFileItem);
            session.getTransaction().commit();
            return deliveryFileItem.getId();
        } catch (Exception e) {
            Helper.showError("Failed to add DeliveryFileItem", e);
            return Helper.UndefinedId;
        }
    }

    @Override
    public boolean updateDeliveryFileItem(DeliveryFileItem deliveryFileItem, int id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            DeliveryFileItem existingItem = session.get(DeliveryFileItem.class, id);
            if (existingItem != null) {
                existingItem.copyFromOther(deliveryFileItem);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            Helper.showError("Failed to update DeliveryFileItem", e);
        }
        return false;
    }

    @Override
    public boolean deleteDeliveryFileItem(int id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            DeliveryFileItem deliveryFileItem = session.get(DeliveryFileItem.class, id);
            if (deliveryFileItem != null) {
                session.remove(deliveryFileItem);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            Helper.showError("Failed to delete DeliveryFileItem", e);
        }
        return false;
    }

    @Override
    public DeliveryFileItem getDeliveryFileItemById(int id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(DeliveryFileItem.class, id);
        } catch (Exception e) {
            Helper.showError("Failed to get DeliveryFileItem by ID", e);
            return null;
        }
    }

    @Override
    public List<DeliveryFileItem> getDeliveryFileItemsByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var query = session.createQuery("FROM DeliveryFileItem WHERE id IN :ids",
                    DeliveryFileItem.class);
            query.setParameterList("ids", ids);
            return query.list();
        } catch (Exception e) {
            Helper.showError("Failed to get DeliveryFileItems by IDs", e);
            return new ArrayList<>();
        }
    }

    @Override
    public ArrayList<DeliveryFileItem> getAllRecords() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var query = session.createQuery("FROM DeliveryFileItem", DeliveryFileItem.class);
            return new ArrayList<>(query.list());
        } catch (Exception e) {
            Helper.showError("Failed to get all DeliveryFileItems", e);
            return new ArrayList<>();
        }
    }
}
