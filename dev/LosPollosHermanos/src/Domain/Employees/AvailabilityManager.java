package Domain.Employees;

import Auxiliary.Enums.Models.AvailabilityStatus;
import DB.WorkersDB.EmployeesDB;
import Models.DBModels.Employees.Availability;
import Models.DBModels.Employees.Employee;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AvailabilityManager {
    //TODO: understand this line
    //private static final IDriverController driverController = DBManager.getInstance().getDriverController();

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
     * Resets the update flags for this availability instance.
     * Call this method to start tracking a new week's availability updates.
     */
    public static void resetUpdateFlags(Availability availability) {
        availability.setUpdatedSun(false);
        availability.setUpdatedMon(false);
        availability.setUpdatedTue(false);
        availability.setUpdatedWed(false);
        availability.setUpdatedThu(false);
    }

    /**
     * Starts a new week for availability tracking.
     * This method resets the update flags for all employees' availability.
     */
    public static void startNewWeek() {
        System.out.println("Starting a new week for availability tracking.");

        // Get all employees and reset their availability update flags
        try {
            List<Employee> allEmployees = EmployeesDB.getMockEmployeeDB();
            for (Employee employee : allEmployees) {
                Availability employeeAvailability = employee.getAvailability();
                if (employeeAvailability != null) {
                    resetUpdateFlags(employeeAvailability);
                    employee.setAvailability(employeeAvailability);
                }
            }
        } catch (Exception e) {
            System.out.println("Error resetting availability update flags: " + e.getMessage());
        }
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
    public static boolean isAvailable(LocalDate shiftDate , Employee employee) {
        // Check if the date is within the next 5 days
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysFromNow = today.plusDays(7);
        if (shiftDate.isBefore(today) || shiftDate.isAfter(sevenDaysFromNow)) {
            return false; // Not available for dates outside the next 5 days
        }

        // Check the day of the week and return availability status
        switch (shiftDate.getDayOfWeek()) {
            case SUNDAY -> {
                return employee.getAvailability().getSunday() != AvailabilityStatus.Unavailable;
            }
            case MONDAY -> {
                return employee.getAvailability().getMonday() != AvailabilityStatus.Unavailable;
            }
            case TUESDAY -> {
                return employee.getAvailability().getTuesday() != AvailabilityStatus.Unavailable;
            }
            case WEDNESDAY -> {
                return employee.getAvailability().getWednesday() != AvailabilityStatus.Unavailable;
            }
            case THURSDAY -> {
                return employee.getAvailability().getThursday() != AvailabilityStatus.Unavailable;
            }
            default -> {
                return false; // Not available for other days
            }
        }
    }

}
