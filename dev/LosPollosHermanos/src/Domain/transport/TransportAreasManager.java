package Domain.transport;

import DB.DBManager;
import DB.interfaces.ITransportAreaController;

public class TransportAreasManager {
    private static final ITransportAreaController transportAreaController = DBManager.getInstance()
            .getTransportAreaController();
}
