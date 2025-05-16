package Models.DBModels.Employees;

import DB.WorkersDB.EmployeesDB;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Represents an employee's availability for shifts on different days of the week.
 * Tracks availability status for Sunday through Thursday and whether each day's
 * availability has been updated for the current week.
 */
public class Availability {
    private AvailabilityStatus sunday;
    private AvailabilityStatus monday;
    private AvailabilityStatus tuesday;
    private AvailabilityStatus wednesday;
    private AvailabilityStatus thursday;
    // boolean values to track if the availability has been updated
    // to support incremental availability updates until Thursday
    private boolean updatedSun;
    private boolean updatedMon;
    private boolean updatedTue;
    private boolean updatedWed;
    private boolean updatedThu;


    /**
     * Creates a new Availability instance with the specified availability statuses for each day.
     *
     * @param sunday The availability status for Sunday
     * @param monday The availability status for Monday
     * @param tuesday The availability status for Tuesday
     * @param wednesday The availability status for Wednesday
     * @param thursday The availability status for Thursday
     */
    public Availability(AvailabilityStatus sunday, AvailabilityStatus monday,
                        AvailabilityStatus tuesday, AvailabilityStatus wednesday,
                        AvailabilityStatus thursday) {
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;

        this.updatedSun = false;
        this.updatedMon = false;
        this.updatedTue = false;
        this.updatedWed = false;
        this.updatedThu = false;
    }

    /**
     * Sets the availability for upcoming shifts for the given employee.
     * If today is Thursday, availability updates are closed for the week.
     *
     * @param employee The employee whose availability is being set
     */
    public static void set_Upcoming_Shifts_Availability(Employee employee) {
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.THURSDAY) {
            System.out.println("Availability updates are closed for this week.");
            System.out.println("Please wait until tomorrow to submit your availability for next week.");
            startNewWeek();
            return;
        }

        Scanner scanner = new Scanner(System.in);

        Availability availability = employee.getAvailability();

        if (availability == null) {
            availability = new Availability(
                    AvailabilityStatus.Unavailable, AvailabilityStatus.Unavailable,
                    AvailabilityStatus.Unavailable, AvailabilityStatus.Unavailable,
                    AvailabilityStatus.Unavailable
            );
        }

        System.out.println("Select Availability for the upcoming shifts");
        System.out.println("Unavailable = 0");
        System.out.println("Day = 1");
        System.out.println("Night = 2");
        System.out.println("Both = 3");
        System.out.println("Skip = 4 *NOTE if shift remains unfilled until Thursday, it will be filled as Unavailable");

        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"};
        for (String day : days) {
            System.out.printf("%s availability:\n", day);
            AvailabilityStatus status = getUserInput(scanner);

            switch (day) {
                case "Sunday" -> {
                    if (status != AvailabilityStatus.Skip) {
                        availability.setSunday(status);
                        availability.setUpdatedSun(true);
                    }
                    else {
                        if (!availability.isUpdatedSun())
                            availability.setSunday(AvailabilityStatus.Unavailable);
                    }
                }
                case "Monday" -> {
                    if (status != AvailabilityStatus.Skip) {
                        availability.setMonday(status);
                        availability.setUpdatedMon(true);
                    }
                    else {
                        if (!availability.isUpdatedMon())
                            availability.setMonday(AvailabilityStatus.Unavailable);
                    }
                }
                case "Tuesday" -> {
                    if (status != AvailabilityStatus.Skip) {
                        availability.setTuesday(status);
                        availability.setUpdatedTue(true);
                    }
                    else {
                        if (!availability.isUpdatedTue())
                            availability.setTuesday(AvailabilityStatus.Unavailable);
                    }
                }
                case "Wednesday" -> {
                    if (status != AvailabilityStatus.Skip) {
                        availability.setWednesday(status);
                        availability.setUpdatedWed(true);
                    }
                    else {
                        if (!availability.isUpdatedWed())
                            availability.setWednesday(AvailabilityStatus.Unavailable);
                    }
                }
                case "Thursday" -> {
                    if (status != AvailabilityStatus.Skip) {
                        availability.setThursday(status);
                        availability.setUpdatedThu(true);
                    }
                    else {
                        if (!availability.isUpdatedThu())
                            availability.setThursday(AvailabilityStatus.Unavailable);
                    }
                }
            }
        }
        employee.setAvailability(availability);
        System.out.println("Availability updated successfully.");
    }

    /**
     * Gets user input for availability status.
     * Prompts the user to enter a number between 0 and 4 representing their availability.
     *
     * @param scanner The Scanner object to read user input
     * @return The selected AvailabilityStatus
     */
    public static AvailabilityStatus getUserInput(Scanner scanner) {
        while (true) {
            System.out.println("Enter (0: Unavailable, 1: Day, 2: Night, 3: Both, 4: Skip): ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 0 and 4.");
                scanner.next(); // consume bad input
                continue;
            }
            int input = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (input) {
                case 0:
                    return AvailabilityStatus.Unavailable;
                case 1:
                    return AvailabilityStatus.Day;
                case 2:
                    return AvailabilityStatus.Night;
                case 3:
                    return AvailabilityStatus.Both;
                case 4:
                    return AvailabilityStatus.Skip;
                default:
                    System.out.println("Number must be between 0 and 4. Try again.");
            }
        }
    }



    /**
     * Checks if the employee is available for a given date.
     * Availability is only checked for dates within the next 5 days.
     *
     * @param shiftDate The date to check availability for
     * @return true if the employee is available on the given date, false otherwise
     */
    public boolean isAvailable(LocalDate shiftDate) {
        // Check if the date is within the next 5 days
        LocalDate today = LocalDate.now();
        LocalDate fiveDaysFromNow = today.plusDays(5);
        if (shiftDate.isBefore(today) || shiftDate.isAfter(fiveDaysFromNow)) {
            return false; // Not available for dates outside the next 5 days
        }

        // Check the day of the week and return availability status
        switch (shiftDate.getDayOfWeek()) {
            case SUNDAY -> {
                return getSunday() != AvailabilityStatus.Unavailable;
            }
            case MONDAY -> {
                return getMonday() != AvailabilityStatus.Unavailable;
            }
            case TUESDAY -> {
                return getTuesday() != AvailabilityStatus.Unavailable;
            }
            case WEDNESDAY -> {
                return getWednesday() != AvailabilityStatus.Unavailable;
            }
            case THURSDAY -> {
                return getThursday() != AvailabilityStatus.Unavailable;
            }
            default -> {
                return false; // Not available for other days
            }
        }
    }
    public static void startNewWeek() {
        System.out.println("Starting a new week for availability tracking.");

        // Get all employees and reset their availability update flags
        try {
            List<Employee> allEmployees = EmployeesDB.getMockEmployeeDB();
            for (Employee employee : allEmployees) {
                Availability availability = employee.getAvailability();
                if (availability != null) {
                    availability.resetUpdateFlags();
                    employee.setAvailability(availability);
                }
            }
        } catch (Exception e) {
            System.out.println("Error resetting availability update flags: " + e.getMessage());
        }
    }

    /**
     * Resets the update flags for this availability instance.
     * Call this method to start tracking a new week's availability updates.
     */
    public void resetUpdateFlags() {
        setUpdatedSun(false);
        setUpdatedMon(false);
        setUpdatedTue(false);
        setUpdatedWed(false);
        setUpdatedThu(false);
    }

    // Getter and setter methods for all private fields

    public AvailabilityStatus getSunday() {
        return sunday;
    }

    public void setSunday(AvailabilityStatus sunday) {
        this.sunday = sunday;
    }

    public AvailabilityStatus getMonday() {
        return monday;
    }

    public void setMonday(AvailabilityStatus monday) {
        this.monday = monday;
    }

    public AvailabilityStatus getTuesday() {
        return tuesday;
    }

    public void setTuesday(AvailabilityStatus tuesday) {
        this.tuesday = tuesday;
    }

    public AvailabilityStatus getWednesday() {
        return wednesday;
    }

    public void setWednesday(AvailabilityStatus wednesday) {
        this.wednesday = wednesday;
    }

    public AvailabilityStatus getThursday() {
        return thursday;
    }

    public void setThursday(AvailabilityStatus thursday) {
        this.thursday = thursday;
    }

    public boolean isUpdatedSun() {
        return updatedSun;
    }

    public void setUpdatedSun(boolean updatedSun) {
        this.updatedSun = updatedSun;
    }

    public boolean isUpdatedMon() {
        return updatedMon;
    }

    public void setUpdatedMon(boolean updatedMon) {
        this.updatedMon = updatedMon;
    }

    public boolean isUpdatedTue() {
        return updatedTue;
    }

    public void setUpdatedTue(boolean updatedTue) {
        this.updatedTue = updatedTue;
    }

    public boolean isUpdatedWed() {
        return updatedWed;
    }

    public void setUpdatedWed(boolean updatedWed) {
        this.updatedWed = updatedWed;
    }

    public boolean isUpdatedThu() {
        return updatedThu;
    }

    public void setUpdatedThu(boolean updatedThu) {
        this.updatedThu = updatedThu;
    }

    @Override
    public String toString() {
        return "Availability=\n" +
                "sunday= " + getSunday() +
                ", monday= " + getMonday() +
                ", tuesday= " + getTuesday() +
                ", wednesday= " + getWednesday() +
                ", thursday= " + getThursday();
    }
}
