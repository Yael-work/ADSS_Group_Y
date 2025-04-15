package DB.interfaces;

import Models.DBModels.TransportArea;

public interface ITransportAreaController {
    /**
     * Adds a transport area to the database.
     *
     * @param transportArea The transport area to add.
     * @return True if the transport area was added successfully, false otherwise.
     */
    boolean addTransportArea(TransportArea transportArea);

    /**
     * Retrieves a transport area from the database by its ID.
     *
     * @param id The ID of the transport area to retrieve.
     * @return The transport area with the specified ID, or null if not found.
     */
    TransportArea getTransportAreaById(int id);

    /**
     * Updates a transport area in the database.
     *
     * @param transportArea The transport area to update.
     * @return True if the transport area was updated successfully, false otherwise.
     */
    boolean updateTransportArea(TransportArea transportArea, int id);

    /**
     * Deletes a transport area from the database by its ID.
     *
     * @param id The ID of the transport area to delete.
     * @return True if the transport area was deleted successfully, false otherwise.
     */
    boolean deleteTransportArea(int id);
}
