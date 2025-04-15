package DB.interfaces;

import Models.DBModels.transport.Branch;

public interface IBranchController {
    /**
     * Adds a new branch to the database.
     *
     * @param branch The branch to be added.
     * @return true if the branch was added successfully, false otherwise.
     */
    boolean addBranch(Branch branch);

    /**
     * Updates an existing branch in the database.
     *
     * @param branch The branch to be updated.
     * @return true if the branch was updated successfully, false otherwise.
     */
    boolean updateBranch(Branch branch);

    /**
     * Deletes a branch from the database.
     *
     * @param id The ID of the branch to be deleted.
     * @return true if the branch was deleted successfully, false otherwise.
     */
    boolean deleteBranch(int id);

    /**
     * Retrieves a branch from the database by its ID.
     *
     * @param id The ID of the branch to be retrieved.
     * @return The branch with the specified ID, or null if not found.
     */
    Branch getBranchById(int id);
}
