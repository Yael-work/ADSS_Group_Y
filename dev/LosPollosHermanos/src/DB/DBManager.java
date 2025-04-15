package DB;

import DB.implementations.*;
import DB.interfaces.*;

public class DBManager {
    static DBManager instance = null;
    private final ISiteController siteController;
    private final IDeliveryFileController deliveryFileController;
    private final ITruckController truckController;
    private final IDeliveryFileItemController deliveryFileItemController;
    private final ITransportAreaController transportAreaController;

    private DBManager() {
        // Private constructor to prevent instantiation
        this.siteController = MockSiteController.getInstance();
        this.deliveryFileController = MockDeliveryFileController.getInstance();
        this.truckController = MockTruckController.getInstance();
        this.deliveryFileItemController = MockDeliveryFileItemController.getInstance();
        this.transportAreaController = MockTransportAreaController.getInstance();
    }

    /**
     * Returns the singleton instance of DBManager.
     *
     * @return The singleton instance of DBManager.
     */
    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    /**
     * Returns the site controller.
     *
     * @return The site controller.
     */
    public final ISiteController getSiteController() {
        return siteController;
    }

    /**
     * Returns the delivery file controller.
     *
     * @return The delivery file controller.
     */
    public final IDeliveryFileController getDeliveryFileController() {
        return deliveryFileController;
    }

    /**
     * Returns the truck controller.
     *
     * @return The truck controller.
     */
    public final ITruckController getTruckController() {
        return truckController;
    }

    /**
     * Returns the delivery file item controller.
     *
     * @return The delivery file item controller.
     */
    public final IDeliveryFileItemController getDeliveryFileItemController() {
        return deliveryFileItemController;
    }

    /**
     * Returns the transport area controller.
     *
     * @return The transport area controller.
     */
    public final ITransportAreaController getTransportAreaController() {
        return transportAreaController;
    }
}
