package DB.implementations;

import Auxiliary.Helper;
import DB.interfaces.IDeliveryFileController;
import Models.DBModels.transport.DeliveryFile;

import java.util.ArrayList;

public class MockDeliveryFileController implements IDeliveryFileController {
    private final ArrayList<DeliveryFile> sites;
    private int currentId = 1;
    private static MockDeliveryFileController instance = null;

    private MockDeliveryFileController() {
        this.sites = new ArrayList<>();
    }

    public static MockDeliveryFileController getInstance() {
        if (instance == null) {
            instance = new MockDeliveryFileController();
        }
        return instance;
    }

    @Override
    public int addDeliveryFile(DeliveryFile deliveryFile) {
        if (deliveryFile == null) {
            return Helper.UndefinedId;
        }
        deliveryFile.setId(currentId++);
        sites.add(deliveryFile);
        return deliveryFile.getId();
    }

    @Override
    public boolean updateDeliveryFile(DeliveryFile deliveryFile, int id) {
        if (deliveryFile == null) {
            return false;
        }
        for (int i = 0; i < sites.size(); i++) {
            if (sites.get(i).getId() == id) {
                deliveryFile.setId(id);
                sites.set(i, deliveryFile);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteDeliveryFile(int id) {
        for (int i = 0; i < sites.size(); i++) {
            if (sites.get(i).getId() == id) {
                sites.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDeliveryFileById(int id) {
        for (DeliveryFile deliveryFile : sites) {
            if (deliveryFile.getId() == id) {
                return deliveryFile.toString();
            }
        }
        return null;
    }

    @Override
    public ArrayList<DeliveryFile> getAllRecords() {
        return new ArrayList<>(sites);
    }
}
