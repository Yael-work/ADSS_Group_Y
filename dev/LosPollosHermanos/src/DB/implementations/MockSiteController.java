package DB.implementations;

import DB.interfaces.ISiteController;
import Models.DBModels.Site;

import java.util.ArrayList;

public class MockSiteController implements ISiteController {
    private final ArrayList<Site> sites;
    private int currentId = 1;
    private static MockSiteController instance = null;

    private MockSiteController() {
        // Private constructor to prevent instantiation
        sites = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of MockSiteManager.
     *
     * @return The singleton instance of MockSiteManager.
     */
    public static MockSiteController getInstance() {
        if (instance == null) {
            instance = new MockSiteController();
        }
        return instance;
    }

    @Override
    public boolean addSite(Site site) {
        if (site == null) {
            return false;
        }
        site.setId(currentId++);
        sites.add(site);
        return true;
    }

    @Override
    public boolean updateSite(Site site, int id) {
        if (site == null || id <= 0) {
            return false;
        }
        for (int i = 0; i < sites.size(); i++) {
            if (sites.get(i).getId() == id) {
                sites.set(i, site);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteSite(int id) {
        if (id <= 0) {
            return false;
        }
        for (int i = 0; i < sites.size(); i++) {
            if (sites.get(i).getId() == id) {
                sites.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public Site getSiteById(int id) {
        if (id <= 0) {
            return null;
        }
        for (Site site : sites) {
            if (site.getId() == id) {
                return site;
            }
        }
        return null;
    }
}
