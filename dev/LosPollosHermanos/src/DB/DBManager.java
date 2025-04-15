package DB;

import DB.implementations.MockSiteController;
import DB.interfaces.ISiteController;

public class DBManager {
    static DBManager instance = null;
    private final ISiteController siteController;

    private DBManager() {
        // Private constructor to prevent instantiation
        this.siteController = MockSiteController.getInstance();
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
}
