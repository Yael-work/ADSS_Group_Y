package DB.implementations;

import Auxiliary.Helper;
import DB.interfaces.ISiteController;
import Models.DBModels.transport.Site;

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
    public int addSite(Site site) {
        if (site == null) {
            return Helper.UndefinedId;
        }
        site.setId(currentId++);
        sites.add(site);
        return site.getId();
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

    @Override
    public Site getSiteBySiteAddress(String siteAddress) {
        if (siteAddress == null || siteAddress.isEmpty()) {
            return null;
        }
        for (Site site : sites) {
            if (site.getSiteAddress().equalsIgnoreCase(siteAddress)) {
                return site;
            }
        }
        return null;
    }

    @Override
    public Site getSiteByContactPhone(String contactPhone) {
        if (contactPhone == null || contactPhone.isEmpty()) {
            return null;
        }
        for (Site site : sites) {
            if (site.getContactPhone().equalsIgnoreCase(contactPhone)) {
                return site;
            }
        }
        return null;
    }

    @Override
    public Site getSiteByContactName(String contactName) {
        if (contactName == null || contactName.isEmpty()) {
            return null;
        }
        for (Site site : sites) {
            if (site.getContactName().equalsIgnoreCase(contactName)) {
                return site;
            }
        }
        return null;
    }

    @Override
    public final ArrayList<Site> getAllRecords() {
        return new ArrayList<>(sites);
    }
}
