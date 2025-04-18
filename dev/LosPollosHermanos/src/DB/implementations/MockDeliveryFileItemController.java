package DB.implementations;

import Auxiliary.Helper;
import DB.interfaces.IDeliveryFileItemController;
import Models.DBModels.transport.DeliveryFileItem;

import java.util.ArrayList;

public class MockDeliveryFileItemController implements IDeliveryFileItemController {
    private final ArrayList<DeliveryFileItem> items;
    private int currentId = 1;
    private static MockDeliveryFileItemController instance = null;

    private MockDeliveryFileItemController() {
        this.items = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of MockDeliveryFileItemController.
     *
     * @return The singleton instance of MockDeliveryFileItemController.
     */
    public static MockDeliveryFileItemController getInstance() {
        if (instance == null) {
            instance = new MockDeliveryFileItemController();
        }
        return instance;
    }

    @Override
    public int addDeliveryFileItem(DeliveryFileItem deliveryFileItem) {
        if (deliveryFileItem == null) {
            return Helper.UndefinedId;
        }
        deliveryFileItem.setId(currentId++);
        items.add(deliveryFileItem);
        return deliveryFileItem.getId();
    }

    @Override
    public boolean updateDeliveryFileItem(DeliveryFileItem deliveryFileItem, int id) {
        if (deliveryFileItem == null) {
            return false;
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                deliveryFileItem.setId(id);
                items.set(i, deliveryFileItem);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteDeliveryFileItem(int id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public DeliveryFileItem getDeliveryFileItemById(int id) {
        for (DeliveryFileItem deliveryFileItem : items) {
            if (deliveryFileItem.getId() == id) {
                return deliveryFileItem;
            }
        }
        return null;
    }

    @Override
    public ArrayList<DeliveryFileItem> getDeliveryFileItemsByIds(ArrayList<Integer> ids) {
        ArrayList<DeliveryFileItem> result = new ArrayList<>();
        for (int id : ids) {
            DeliveryFileItem deliveryFileItem = getDeliveryFileItemById(id);
            if (deliveryFileItem != null) {
                result.add(deliveryFileItem);
            }
        }
        return result;
    }

    @Override
    public final ArrayList<DeliveryFileItem> getAllRecords() {
        return new ArrayList<>(items);
    }
}
