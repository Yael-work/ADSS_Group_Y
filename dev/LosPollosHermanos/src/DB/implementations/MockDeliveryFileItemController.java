package DB.implementations;

import DB.interfaces.IDeliveryFileItemController;
import Models.DBModels.transport.DeliveryFileItem;

import java.util.ArrayList;

public class MockDeliveryFileItemController implements IDeliveryFileItemController {
    private final ArrayList<DeliveryFileItem> sites;
    private int currentId = 1;
    private static MockDeliveryFileItemController instance = null;

    private MockDeliveryFileItemController() {
        this.sites = new ArrayList<>();
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
    public boolean addDeliveryFileItem(DeliveryFileItem deliveryFileItem) {
        if (deliveryFileItem == null) {
            return false;
        }
        deliveryFileItem.setId(currentId++);
        sites.add(deliveryFileItem);
        return true;
    }

    @Override
    public boolean updateDeliveryFileItem(DeliveryFileItem deliveryFileItem, int id) {
        if (deliveryFileItem == null) {
            return false;
        }
        for (int i = 0; i < sites.size(); i++) {
            if (sites.get(i).getId() == id) {
                deliveryFileItem.setId(id);
                sites.set(i, deliveryFileItem);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteDeliveryFileItem(int id) {
        for (int i = 0; i < sites.size(); i++) {
            if (sites.get(i).getId() == id) {
                sites.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public DeliveryFileItem getDeliveryFileItemById(int id) {
        for (DeliveryFileItem deliveryFileItem : sites) {
            if (deliveryFileItem.getId() == id) {
                return deliveryFileItem;
            }
        }
        return null;
    }
}
