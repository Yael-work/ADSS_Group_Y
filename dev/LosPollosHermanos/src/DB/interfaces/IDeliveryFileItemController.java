package DB.interfaces;

import Models.DBModels.transport.DeliveryFileItem;

import java.util.ArrayList;

public interface IDeliveryFileItemController extends IController<DeliveryFileItem> {
    /**
     * Adds a new delivery file item to the database.
     *
     * @param deliveryFileItem The delivery file item to be added.
     * @return true if the delivery file item was added successfully, false otherwise.
     */
    boolean addDeliveryFileItem(DeliveryFileItem deliveryFileItem);

    /**
     * Updates an existing delivery file item in the database.
     *
     * @param deliveryFileItem The delivery file item to be updated.
     * @param id               The ID of the delivery file item to be updated.
     * @return true if the delivery file item was updated successfully, false otherwise.
     */
    boolean updateDeliveryFileItem(DeliveryFileItem deliveryFileItem, int id);

    /**
     * Deletes a delivery file item from the database.
     *
     * @param id The ID of the delivery file item to be deleted.
     * @return true if the delivery file item was deleted successfully, false otherwise.
     */
    boolean deleteDeliveryFileItem(int id);

    /**
     * Retrieves a delivery file item from the database by its ID.
     *
     * @param id The ID of the delivery file item to be retrieved.
     * @return The delivery file item with the specified ID, or null if not found.
     */
    DeliveryFileItem getDeliveryFileItemById(int id);

    /**
     * Retrieves delivery file items from the database by their IDs.
     *
     * @param ids The list of IDs of the delivery file items to be retrieved.
     * @return A list of delivery file items with the specified IDs, or an empty list if none found.
     */
    ArrayList<DeliveryFileItem> getDeliveryFileItemsByIds(ArrayList<Integer> ids);
}
