package Models.DBModels.Employees;

import Auxiliary.IdGenerator;
import DB.WorkersDB.EmployeesDB;
import DB.WorkersDB.ShiftsDB;
import Models.DBModels.BaseModel;
import Models.DBModels.transport.Branch;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shift extends BaseModel {
    public static LocalTime START_HOUR_DAY = LocalTime.of(8, 0);
    public static LocalTime END_HOUR_DAY = LocalTime.of(15, 0);
    public static LocalTime START_HOUR_NIGHT = LocalTime.of(16, 0);
    public static LocalTime END_HOUR_NIGHT = LocalTime.of(23, 0);
    public static Map<String, Integer> REQUIRED_ROLES_FOR_SHIFT = new HashMap<>(){{
        put("Shift Manager", 1);
        put("Cashier", 1);
        put("Cleaner", 1);
        put("Security", 1);
    }};

    private final String shiftID;
    private final LocalDate shiftDate;
    private final String shiftType;
    private LocalTime startShiftTime;
    private LocalTime endShiftTime;
    private Employee shiftManager;
    private Map<Employee,String> assignedEmployees;
    private List<Employee> allAvailableEmployees;
    private Map<String, Integer> requiredRoles;
    private static int shiftCounter = 1;
    private Branch branch;

    public Shift(int id, LocalDate shiftDate, String shiftType,
                 Employee shiftManager, Map<Employee,String> assignedEmployees,
                 List<Employee> allAvailableEmployees , Branch branch) {
        super(id, Shift.class.getName());
        this.shiftID = generateSequentialShiftId();
        this.shiftDate = shiftDate;
        this.shiftType = shiftType;
        //method to check the sart shft time via the shift type
        LocalTime startShiftTime = getStartShiftTime();
        LocalTime endShiftTime = getEndShiftTime();
        this.startShiftTime = startShiftTime;
        this.endShiftTime = endShiftTime;
        this.shiftManager = shiftManager;
        this.branch = branch;

        // Create a new list for available employees to avoid modifying the input list
        this.allAvailableEmployees = new ArrayList<>(allAvailableEmployees);

        // Create a new map for assigned employees to avoid modifying the input map
        this.assignedEmployees = new HashMap<>(assignedEmployees);

        // Remove assigned employees from the available list
        // An employee cannot be both assigned and available at the same time
        for (Employee employee : this.assignedEmployees.keySet()) {
            this.allAvailableEmployees.remove(employee);
        }

        this.requiredRoles = new HashMap<>(getRequiredRoles());
    }
    public String getShiftID() {
        return shiftID;
    }

    public LocalDate getShiftDate() {
        return shiftDate;
    }


    public String getShiftType() {
        return shiftType;
    }

    public LocalTime getStartShiftTime() {
        // During initialization, get from global variables
        if (this.startShiftTime == null) {
            if (shiftType.equals("Day")) {
                return START_HOUR_DAY;
            } else if (shiftType.equals("Night")) {
                return START_HOUR_NIGHT;
            } else {
                throw new IllegalArgumentException("Invalid shift type: " + shiftType);
            }
        }
        // After initialization, return the local field
        return this.startShiftTime;
    }

    public static void setStartHourDay(LocalTime startShiftTime) {
        START_HOUR_DAY = startShiftTime;
    }



    public static void setStartHourNight(LocalTime startShiftTime) {
        START_HOUR_NIGHT = startShiftTime;
    }


    public LocalTime getEndShiftTime() {
        // During initialization, get from global variables
        if (this.endShiftTime == null) {
            if (shiftType.equals("Day")) {
                return END_HOUR_DAY;
            } else if (shiftType.equals("Night")) {
                return END_HOUR_NIGHT;
            } else {
                throw new IllegalArgumentException("Invalid shift type: " + shiftType);
            }
        }
        // After initialization, return the local field
        return this.endShiftTime;
    }

    public static void setEndHourDay(LocalTime endShiftTime) {
        END_HOUR_DAY = endShiftTime;
    }


    public static void setEndHourNight(LocalTime endShiftTime) {
        END_HOUR_NIGHT = endShiftTime;
    }


    public Map<Employee,String> getAssignedEmployees() {
        return assignedEmployees;
    }

    public List<Employee> getAllAvailableEmployees() {return allAvailableEmployees;}

    public void addAssignedEmployee(Employee employee) {
        // Get the first role from the employee's role list
        String roleName = employee.getRolesList().isEmpty() ? "Unknown" : employee.getRolesList().get(0).getRole();
        this.assignedEmployees.put(employee, roleName);
        this.allAvailableEmployees.remove(employee);
    }

    public void addAssignedEmployee(Employee employee, String role) {
        this.assignedEmployees.put(employee, role);
        this.allAvailableEmployees.remove(employee);
    }

    public void removeAssignedEmployee(Employee employee) {
        this.assignedEmployees.remove(employee);
        if (allAvailableEmployees.contains(employee)) {
            System.out.println("Employee is already available.");
        } else {
            this.allAvailableEmployees.add(employee);
        }
    }


    public static Map<String, Integer> getRequiredRoles() {
        return REQUIRED_ROLES_FOR_SHIFT;
    }


    public static void addRequiredRoles(String role, int count) {
        REQUIRED_ROLES_FOR_SHIFT.put(role, count);
    }

    public static void removeRequiredRole(String role) {
        REQUIRED_ROLES_FOR_SHIFT.remove(role);
    }

    // method to generate a unique shift ID
    private static synchronized String generateSequentialShiftId() {
        return "SHIFT-" + (shiftCounter++);
    }

    @Override
    public String toString() {
        return "ShiftID= '" + shiftID + '\'' +
                ", ShiftDate= " + shiftDate +
                ", ShiftType= '" + shiftType + '\'';
    }

    @Override
    public boolean copyFromOther(BaseModel other, Session session) {
        return false;
    }

    private static boolean isEmployeeAvailableForShift(Employee employee, LocalDate shiftDate, String shiftType) {
        if (employee == null) {
            return false;
        }

        Availability availability = employee.getAvailability();
        if (availability == null) {
            return false;
        }

        // First check if the employee is available on this date
        if (!availability.isAvailable(shiftDate)) {
            return false;
        }

        // Then check if the employee is available for this shift type
        AvailabilityStatus status;
        switch (shiftDate.getDayOfWeek()) {
            case SUNDAY -> status = availability.getSunday();
            case MONDAY -> status = availability.getMonday();
            case TUESDAY -> status = availability.getTuesday();
            case WEDNESDAY -> status = availability.getWednesday();
            case THURSDAY -> status = availability.getThursday();
            default -> status = AvailabilityStatus.Unavailable;
        }

        // Check if the employee's availability status matches the shift type
        return switch (status) {
            case Day -> shiftType.equals("Day");
            case Night -> shiftType.equals("Night");
            case Both -> true; // Available for both day and night shifts
            default -> false; // Unavailable or Skip
        };
    }

    public static void createShiftsDB() {
        List<Employee> allEmployees = EmployeesDB.getMockEmployeeDB();

        if (allEmployees.isEmpty()) {
            System.out.println("No employees found to create shifts.");
            return;
        }

        // Create mock available and assigned employees for shift 1 (Day shift on 2025-5-22)
        List<Employee> availableEmployees1 = new ArrayList<>();
        Map<Employee, String> assignedEmployees1 = new HashMap<>();
        LocalDate shift1Date = LocalDate.of(2025, 5, 22);
        String shift1Type = "Day";

        // First add all potentially available employees
        Employee emp002 = EmployeesDB.findEmployeeById("EMP002");
        Employee emp003 = EmployeesDB.findEmployeeById("EMP003");
        Employee emp005 = EmployeesDB.findEmployeeById("EMP005");

        // Check if employees are actually available for this shift based on their availability status
        if (isEmployeeAvailableForShift(emp002, shift1Date, shift1Type)) {
            availableEmployees1.add(emp002);
        }
        if (isEmployeeAvailableForShift(emp003, shift1Date, shift1Type)) {
            availableEmployees1.add(emp003);
        }
        if (isEmployeeAvailableForShift(emp005, shift1Date, shift1Type)) {
            availableEmployees1.add(emp005);
        }

        // Get the first role from the employee's role list
        String role002 = emp002.getRolesList().isEmpty() ? "Unknown" : emp002.getRolesList().get(0).getRole();
        String role005 = emp005.getRolesList().isEmpty() ? "Unknown" : emp005.getRolesList().get(0).getRole();

        // Only assign employees who are available
        if (isEmployeeAvailableForShift(emp002, shift1Date, shift1Type)) {
            assignedEmployees1.put(emp002, role002);
            // Remove from available list since they're now assigned
            availableEmployees1.remove(emp002);
        }
        if (isEmployeeAvailableForShift(emp005, shift1Date, shift1Type)) {
            assignedEmployees1.put(emp005, role005);
            // Remove from available list since they're now assigned
            availableEmployees1.remove(emp005);
        }

        // Create mock available and assigned employees for shift 2 (Night shift on 2025-5-21)
        List<Employee> availableEmployees2 = new ArrayList<>();
        Map<Employee, String> assignedEmployees2 = new HashMap<>();
        LocalDate shift2Date = LocalDate.of(2025, 5, 21);
        String shift2Type = "Night";

        // First add all potentially available employees
        Employee emp002_2 = EmployeesDB.findEmployeeById("EMP002");
        Employee emp004 = EmployeesDB.findEmployeeById("EMP004");
        Employee emp005_2 = EmployeesDB.findEmployeeById("EMP005");

        // Check if employees are actually available for this shift based on their availability status
        if (isEmployeeAvailableForShift(emp002_2, shift2Date, shift2Type)) {
            availableEmployees2.add(emp002_2);
        }
        if (isEmployeeAvailableForShift(emp004, shift2Date, shift2Type)) {
            availableEmployees2.add(emp004);
        }
        if (isEmployeeAvailableForShift(emp005_2, shift2Date, shift2Type)) {
            availableEmployees2.add(emp005_2);
        }

        String role002_2 = emp002_2.getRolesList().isEmpty() ? "Unknown" : emp002_2.getRolesList().get(0).getRole();
        String role004 = emp004.getRolesList().isEmpty() ? "Unknown" : emp004.getRolesList().get(0).getRole();

        // Only assign employees who are available
        if (isEmployeeAvailableForShift(emp002_2, shift2Date, shift2Type)) {
            assignedEmployees2.put(emp002_2, role002_2);
            // Remove from available list since they're now assigned
            availableEmployees2.remove(emp002_2);
        }
        if (isEmployeeAvailableForShift(emp004, shift2Date, shift2Type)) {
            assignedEmployees2.put(emp004, role004);
            // Remove from available list since they're now assigned
            availableEmployees2.remove(emp004);
        }

        // Create mock available and assigned employees for shift 3 (Day shift on 2025-5-20)
        List<Employee> availableEmployees3 = new ArrayList<>();
        Map<Employee, String> assignedEmployees3 = new HashMap<>();
        LocalDate shift3Date = LocalDate.of(2025, 5, 20);
        String shift3Type = "Day";

        // First add all potentially available employees
        Employee emp001 = EmployeesDB.findEmployeeById("EMP001");
        Employee emp002_3 = EmployeesDB.findEmployeeById("EMP002");
        Employee emp003_3 = EmployeesDB.findEmployeeById("EMP003");
        Employee emp004_3 = EmployeesDB.findEmployeeById("EMP004");

        // Check if employees are actually available for this shift based on their availability status
        if (isEmployeeAvailableForShift(emp001, shift3Date, shift3Type)) {
            availableEmployees3.add(emp001);
        }
        if (isEmployeeAvailableForShift(emp002_3, shift3Date, shift3Type)) {
            availableEmployees3.add(emp002_3);
        }
        if (isEmployeeAvailableForShift(emp003_3, shift3Date, shift3Type)) {
            availableEmployees3.add(emp003_3);
        }
        if (isEmployeeAvailableForShift(emp004_3, shift3Date, shift3Type)) {
            availableEmployees3.add(emp004_3);
        }

        String role002_3 = emp002_3.getRolesList().isEmpty() ? "Unknown" : emp002_3.getRolesList().get(0).getRole();
        String role001 = emp001.getRolesList().isEmpty() ? "Unknown" : emp001.getRolesList().get(0).getRole();

        // Only assign employees who are available
        if (isEmployeeAvailableForShift(emp002_3, shift3Date, shift3Type)) {
            assignedEmployees3.put(emp002_3, role002_3);
            // Remove from available list since they're now assigned
            availableEmployees3.remove(emp002_3);
        }
        if (isEmployeeAvailableForShift(emp001, shift3Date, shift3Type)) {
            assignedEmployees3.put(emp001, role001);
            // Remove from available list since they're now assigned
            availableEmployees3.remove(emp001);
        }

        Shift shift1 = new Shift(
                IdGenerator.getNextIdShift(),
                LocalDate.of(2025, 5, 22),
                "Day",
                EmployeesDB.findEmployeeById("EMP002"), // Manager
                assignedEmployees1,
                availableEmployees1,null
        );

        Shift shift2 = new Shift(
                IdGenerator.getNextIdShift(),
                LocalDate.of(2025, 5, 21),
                "Night",
                EmployeesDB.findEmployeeById("EMP002"), // Manager
                assignedEmployees2,
                availableEmployees2,null
        );

        Shift shift3 = new Shift(
                IdGenerator.getNextIdShift(),
                LocalDate.of(2025, 5, 20),
                "Day",
                EmployeesDB.findEmployeeById("EMP002"), // Manager
                assignedEmployees3,
                availableEmployees3,null
        );

        // Assuming you have a DB class to hold shifts
        ShiftsDB.addUpcomingShift(shift1);
        ShiftsDB.addUpcomingShift(shift2);
        ShiftsDB.addUpcomingShift(shift3);
    }


    public Branch getBranch() {
        return branch;
    }
}
