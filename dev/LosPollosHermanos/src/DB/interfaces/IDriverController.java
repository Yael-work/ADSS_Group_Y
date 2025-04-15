package DB.interfaces;
import Models.DBModels.transport.Driver;

public interface IDriverController   {
    /**
     * Adds a new driver to the database.
     *
     * @param driver The driver to be added.
     * @return true if the driver was added successfully, false otherwise.
     */
    boolean addDriver(Driver driver);

    /**
     * Updates an existing driver in the database.
     *
     * @param driver The driver to be updated.
     * @return true if the driver was updated successfully, false otherwise.
     */
    boolean updateDriver(Driver driver, int id);

    /**
     * Deletes a driver from the database.
     *
     * @param id The ID of the driver to be deleted.
     * @return true if the driver was deleted successfully, false otherwise.
     */
    boolean deleteDriver(int id);

    /**
     * Retrieves a driver from the database by its ID.
     *
     * @param id The ID of the driver to be retrieved.
     * @return The driver with the specified ID, or null if not found.
     */
    Driver getDriverById(int id);
}
