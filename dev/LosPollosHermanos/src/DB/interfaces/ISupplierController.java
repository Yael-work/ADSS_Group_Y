package DB.interfaces;

import Models.DBModels.suppliers.Supplier;

public interface ISupplierController extends IController<Supplier>{
    /**
     * Adds a supplier to the database.
     *
     * @param supplier The supplier to add.
     * @return True if the supplier was added successfully, false otherwise.
     */
    boolean addSupplier(Supplier supplier);

    /**
     * Updates a supplier in the database.
     *
     * @param supplier The supplier to update.
     * @return True if the supplier was updated successfully, false otherwise.
     */
    boolean updateSupplier(Supplier supplier, int id);

    /**
     * Deletes a supplier from the database.
     *
     * @param id The ID of the supplier to delete.
     * @return True if the supplier was deleted successfully, false otherwise.
     */
    boolean deleteSupplier(int id);

    /**
     * Retrieves a supplier from the database by ID.
     *
     * @param id The ID of the supplier to retrieve.
     * @return The supplier with the specified ID, or null if not found.
     */
    Supplier getSupplierById(int id);
}
