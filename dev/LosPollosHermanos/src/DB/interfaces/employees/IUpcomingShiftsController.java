package DB.interfaces.employees;

import DB.interfaces.transport.IController;
import Models.DBModels.Employees.Shift;

public interface IUpcomingShiftsController extends IController <Shift> {
    public static void addUpcomingShift(Shift shift);
    public static void removeUpcomingShift(Shift shift);
    public static Shift findShiftById(String shiftId);
}
