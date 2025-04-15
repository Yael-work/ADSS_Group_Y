package DB.interfaces;

import Models.DBModels.transport.Site;

public interface ISiteController extends IController<Site> {
    /**
     * Adds a new site to the database.
     *
     * @param site The site to be added.
     * @return true if the site was added successfully, false otherwise.
     */
    boolean addSite(Site site);

    /**
     * Updates an existing site in the database.
     *
     * @param site The site to be updated.
     * @return true if the site was updated successfully, false otherwise.
     */
    boolean updateSite(Site site, int id);

    /**
     * Deletes a site from the database.
     *
     * @param id The ID of the site to be deleted.
     * @return true if the site was deleted successfully, false otherwise.
     */
    boolean deleteSite(int id);

    /**
     * Retrieves a site from the database by its ID.
     *
     * @param id The ID of the site to be retrieved.
     * @return The site with the specified ID, or null if not found.
     */
    Site getSiteById(int id);
}
