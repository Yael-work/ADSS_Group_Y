package DB.interfaces;

import Models.DBModels.transport.DeliveryFile;

public interface IDeliveryFileController extends IController<DeliveryFile> {

    /**
     * Adds a new delivery file to the database.
     *
     * @param deliveryFile The delivery file to be added.
     * @return The ID of the newly added delivery file, or -1 if the operation failed.
     */
    int addDeliveryFile(DeliveryFile deliveryFile);

    /**
     * Updates an existing delivery file in the database.
     *
     * @param deliveryFile The delivery file to be updated.
     * @param id           The ID of the delivery file to be updated.
     * @return true if the delivery file was updated successfully, false otherwise.
     */
    boolean updateDeliveryFile(DeliveryFile deliveryFile, int id);

    /**
     * Deletes a delivery file from the database.
     *
     * @param id The ID of the delivery file to be deleted.
     * @return true if the delivery file was deleted successfully, false otherwise.
     */
    boolean deleteDeliveryFile(int id);

    /**
     * Retrieves a delivery file from the database by its ID.
     *
     * @param id The ID of the delivery file to be retrieved.
     * @return The path of the delivery file with the specified ID, or null if not found.
     */
    DeliveryFile getDeliveryFileById(int id);
}
