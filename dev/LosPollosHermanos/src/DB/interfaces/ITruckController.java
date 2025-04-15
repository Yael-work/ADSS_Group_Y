package DB.interfaces;

import Models.DBModels.transport.Truck;

public interface ITruckController extends IController<Truck> {
    /**
     * Adds a new truck to the database.
     *
     * @param truck The truck to be added.
     * @return true if the truck was added successfully, false otherwise.
     */
    boolean addTruck(Truck truck);

    /**
     * Updates an existing truck in the database.
     *
     * @param truck The truck to be updated.
     * @return true if the truck was updated successfully, false otherwise.
     */
    boolean updateTruck(Truck truck, int id);

    /**
     * Deletes a truck from the database.
     *
     * @param id The ID of the truck to be deleted.
     * @return true if the truck was deleted successfully, false otherwise.
     */
    boolean deleteTruck(int id);

    /**
     * Retrieves a truck from the database by its ID.
     *
     * @param id The ID of the truck to be retrieved.
     * @return The truck with the specified ID, or null if not found.
     */
    Truck getTruckById(int id);


}
