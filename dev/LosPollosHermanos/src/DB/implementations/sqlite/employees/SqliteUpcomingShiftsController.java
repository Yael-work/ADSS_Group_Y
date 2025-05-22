package DB.implementations.sqlite.employees;

import Models.DBModels.Employees.Shift;

import java.util.ArrayList;
import java.util.List;

public class SqliteUpcomingShiftsController {


    // upcomingShiftsDB methods
    public static void addUpcomingShift(Shift shift) {
        upcomingShiftsDB.add(shift);
    }

    public static List<Shift> getUpcomingShifts() {
        return new ArrayList<>(upcomingShiftsDB); // return copy for safety
    }

    public static void removeUpcomingShift(Shift shift) {
        upcomingShiftsDB.remove(shift);
    }


    public static Shift findShiftById(String shiftId) {
        for (Shift shift : upcomingShiftsDB) {
            if (shift.getShiftID().equals(shiftId)) {
                return shift;
            }
        }
        return null;
    }



}
