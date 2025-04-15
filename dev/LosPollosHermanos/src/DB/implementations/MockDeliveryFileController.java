package DB.implementations;

import DB.interfaces.IDeliveryFileController;
import Models.DBModels.DeliveryFile;

public class MockDeliveryFileController implements IDeliveryFileController {
    @Override
    public boolean addDeliveryFile(DeliveryFile deliveryFile) {
        return false;
    }

    @Override
    public boolean updateDeliveryFile(DeliveryFile deliveryFile, int id) {
        return false;
    }

    @Override
    public boolean deleteDeliveryFile(int id) {
        return false;
    }

    @Override
    public String getDeliveryFileById(int id) {
        return "";
    }
}
