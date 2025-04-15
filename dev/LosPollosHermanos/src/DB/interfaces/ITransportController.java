package DB.interfaces;
import Models.DBModels.transport.Transport;

public interface ITransportController extends IController<Transport>{
    /**
     * Adds new transport to the database.
     *
     * @param transport The transport to add.
     * @return true if the transport was added successfully, false otherwise.
     */
    boolean addTransport(Transport transport);
    /**
     * Updates existing transport in the database.
     *
     * @param transport The transport with updated information.
     * @param id       The ID of the transport to update.
     * @return true if the transport was updated successfully, false otherwise.
     */
    boolean updateTransport(Transport transport, int id);
    /**
     * Deletes transport from the database.
     *
     * @param id The ID of the transport to delete.
     * @return true if the transport was deleted successfully, false otherwise.
     */
    boolean deleteTransport(int id);
    /**
     * Retrieves transport by its ID.
     *
     * @param id The ID of the transport to retrieve.
     * @return The transport with the specified ID, or null if not found.
     */
    Transport getTransportById(int id);
}
