package DB;

import DB.implementations.MockDeliveryFileController;
import DB.implementations.MockSiteController;
import DB.interfaces.IDeliveryFileController;
import DB.interfaces.ISiteController;
import DB.interfaces.ITruckController;
import DB.implementations.MockTruckController;

public class DBManager {
    static DBManager instance = null;
    private final ISiteController siteController;
    private final IDeliveryFileController deliveryFileController;
    private final ITruckController truckController;

    private DBManager() {
        // Private constructor to prevent instantiation
        this.siteController = MockSiteController.getInstance();
        this.deliveryFileController = MockDeliveryFileController.getInstance();
        this.truckController = MockTruckController.getInstance();
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
}
