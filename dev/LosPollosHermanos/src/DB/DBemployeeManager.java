package DB;

import DB.implementations.sqlite.employees.*;
import DB.interfaces.employees.*;

public class DBemployeeManager {
    static DBemployeeManager instance = null;
    private final IEmployeeController employeeController;
    private final IPastShiftsController pastShiftsController;
    private final IUpcomingShiftsController upcomingShiftsController;

    private DBemployeeManager() {
        this.employeeController = SqliteEmployeesController.getInstance();
        this.pastShiftsController = SqlitePastShiftsController.getInstance();
        this.upcomingShiftsController = SqliteUpcomingShiftsController.getInstance();
    }

    /**
     * Returns the singleton instance of DBManager.
     *
     * @return The singleton instance of DBManager.
     */
    public static DBemployeeManager getInstance() {
        if (instance == null) {
            instance = new DBemployeeManager();
        }
        return instance;
    }

    /**
     * Returns the employee controller.
     *
     * @return The employee controller.
     */
    public final IEmployeeController getEmployeeController() {
        return employeeController;
    }
}
