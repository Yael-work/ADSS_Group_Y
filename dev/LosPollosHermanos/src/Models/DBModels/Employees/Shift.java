package Models.DBModels.Employees;

import Auxiliary.IdGenerator;
import DB.WorkersDB.EmployeesDB;
import DB.WorkersDB.ShiftsDB;
import Models.DBModels.BaseModel;
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
    private List<Employee> assignedEmployees;
    private List<Employee> allAvailableEmployees;
    private Map<String, Integer> requiredRoles;
    private static int shiftCounter = 1;

    public Shift(int id, LocalDate shiftDate, String shiftType,
                 Employee shiftManager, List<Employee> assignedEmployees,
                 List<Employee> allAvailableEmployees) {
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
        this.assignedEmployees = assignedEmployees;
        this.allAvailableEmployees = allAvailableEmployees;
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


    public List<Employee> getAssignedEmployees() {
        return assignedEmployees;
    }

    public void addAssignedEmployee(Employee employee) {
        this.assignedEmployees.add(employee);
        if (this.assignedEmployees.size() == this.requiredRoles.size()) {
            System.out.println("Now All required roles are filled.");
        } else if (this.assignedEmployees.size() > this.requiredRoles.size()) {
            System.out.println("Now More roles than required.");
        }
        else {
            System.out.println("Now Not all required roles are filled.");
        }
    }

    public void removeAssignedEmployee(Employee employee) {
        this.assignedEmployees.remove(employee);
        if (this.assignedEmployees.size() == this.requiredRoles.size()) {
            System.out.println("Now all required roles are filled.");
        } else if (this.assignedEmployees.size() > this.requiredRoles.size()) {
            System.out.println("Now More roles than required.");
        }
        else {
            System.out.println("Now Not all required roles are filled.");
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

    public static void createShiftsDB() {
        List<Employee> allEmployees = EmployeesDB.getMockEmployeeDB();

        if (allEmployees.isEmpty()) {
            System.out.println("No employees found to create shifts.");
            return;
        }
        // Create mock available and assigned employees
        List<Employee> availableEmployees1 = new ArrayList<>();
        List<Employee> assignedEmployees1 = new ArrayList<>();

        availableEmployees1.add(EmployeesDB.findEmployeeById("EMP002"));
        availableEmployees1.add(EmployeesDB.findEmployeeById("EMP003"));
        availableEmployees1.add(EmployeesDB.findEmployeeById("EMP005"));
        assignedEmployees1.add(EmployeesDB.findEmployeeById("EMP002"));
        assignedEmployees1.add(EmployeesDB.findEmployeeById("EMP005"));

        List<Employee> availableEmployees2 = new ArrayList<>();
        List<Employee> assignedEmployees2 = new ArrayList<>();

        availableEmployees1.add(EmployeesDB.findEmployeeById("EMP002"));
        availableEmployees1.add(EmployeesDB.findEmployeeById("EMP004"));
        availableEmployees1.add(EmployeesDB.findEmployeeById("EMP005"));
        assignedEmployees1.add(EmployeesDB.findEmployeeById("EMP002"));
        assignedEmployees1.add(EmployeesDB.findEmployeeById("EMP004"));

        List<Employee> availableEmployees3 = new ArrayList<>();
        List<Employee> assignedEmployees3 = new ArrayList<>();

        availableEmployees1.add(EmployeesDB.findEmployeeById("EMP001"));
        availableEmployees1.add(EmployeesDB.findEmployeeById("EMP002"));
        availableEmployees1.add(EmployeesDB.findEmployeeById("EMP004"));
        availableEmployees1.add(EmployeesDB.findEmployeeById("EMP003"));
        assignedEmployees1.add(EmployeesDB.findEmployeeById("EMP002"));
        assignedEmployees1.add(EmployeesDB.findEmployeeById("EMP001"));

        Shift shift1 = new Shift(
                IdGenerator.getNextIdShift(),
                LocalDate.of(2025, 5, 1),
                "Day",
                EmployeesDB.findEmployeeById("EMP002"), // Manager
                assignedEmployees1,
                availableEmployees1
        );

        Shift shift2 = new Shift(
                IdGenerator.getNextIdShift(),
                LocalDate.of(2025, 4, 27),
                "Night",
                EmployeesDB.findEmployeeById("EMP002"), // Manager
                assignedEmployees2,
                availableEmployees2
        );

        Shift shift3 = new Shift(
                IdGenerator.getNextIdShift(),
                LocalDate.of(2025, 4, 29),
                "Day",
                EmployeesDB.findEmployeeById("EMP002"), // Manager
                assignedEmployees3,
                availableEmployees3
        );

        // Assuming you have a DB class to hold shifts
        ShiftsDB.addUpcomingShift(shift1);
        ShiftsDB.addUpcomingShift(shift2);
        ShiftsDB.addUpcomingShift(shift3);
    }


    @Override
    public boolean copyFromOther(BaseModel other, Session session) {
        return false;
    }
}
