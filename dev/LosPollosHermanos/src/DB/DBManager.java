package DB;

import DB.implementations.MockDeliveryFileController;
import DB.implementations.MockDeliveryFileItemController;
import DB.implementations.MockSiteController;
import DB.interfaces.IDeliveryFileController;
import DB.interfaces.IDeliveryFileItemController;
import DB.interfaces.ISiteController;
import DB.interfaces.ITruckController;
import DB.implementations.MockTruckController;
import DB.interfaces.IDriverController;
import DB.implementations.MockDriverController;

public class DBManager {
    static DBManager instance = null;
    private final ISiteController siteController;
    private final IDeliveryFileController deliveryFileController;
    private final ITruckController truckController;
    private final IDeliveryFileItemController deliveryFileItemController;
    private final IDriverController driverController;

    private DBManager() {
        // Private constructor to prevent instantiation
        this.siteController = MockSiteController.getInstance();
        this.deliveryFileController = MockDeliveryFileController.getInstance();
        this.truckController = MockTruckController.getInstance();
        this.deliveryFileItemController = MockDeliveryFileItemController.getInstance();
        this.driverController = MockDriverController.getInstance();
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
     * Returns the driver controller.
     *
     * @return The driver controller.
     */
    public final IDriverController getDriverController() {
        return driverController;
    }
}
