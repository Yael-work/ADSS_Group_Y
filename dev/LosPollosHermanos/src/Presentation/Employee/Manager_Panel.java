package Presentation.Employee;

import Auxiliary.IdGenerator;
import DB.WorkersDB.EmployeesDB;
import DB.WorkersDB.ShiftsDB;
import Models.DBModels.Employees.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Auxiliary.sharedFunctions;

import static DB.WorkersDB.ShiftsDB.findShiftById;

public class Manager_Panel {

    private static void printMenu_Manager() {
        System.out.println("\nWelcome to the Manager Panel!\n");
        System.out.println("Please choose an action (1,2,3,4 or 5):");
        System.out.println("1. create New Shift");
        System.out.println("2. Remove Employee from Shift");
        System.out.println("3. Add Employee to Shift");
        System.out.println("4. Promote Employee");
        System.out.println("5. Use cancel card");
        System.out.println("6. Go back to General Employee panel");
    }

    public static void moveToPanel_Manager(Employee employee) {
        Scanner scanner = new Scanner(System.in);
        do {
            printMenu_Manager();
            int choice = getValidIntChoice(scanner, 1, 6);

            switch (choice) {
                case 1 -> createNewShift(employee);
                case 2 -> removeEmployeeFromShift(employee);
                case 3 -> addEmployeeToShift();
                case 4 -> promoteEmployee();
                case 5 -> useCancelCard(employee);
                case 6 -> {
                    System.out.println("Going back to General Employee panel");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }while (true);
    }

    private static void useCancelCard(Employee manager) {
        manager.use_cancel_card();
    }


    //case 1 in switch case printMenu_Manager
    public static void createNewShift(Employee manager) {
        Scanner scanner = new Scanner(System.in);
        LocalDate shiftDate = sharedFunctions.parseDate(scanner, "Please enter your start date (dd/MM/yyyy):");

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.SUNDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.THURSDAY); // THURSDAY

        if (shiftDate.isBefore(today)) {
            System.out.println("Shift date cannot be in the past.");
            return;
        }


        String shiftType = sharedFunctions.getShiftTypeFromUser(scanner);

        List<Employee> assignedEmployeesToShift = new ArrayList<>();
        List<Employee> allAvailableEmployees = new ArrayList<>();

        for (Employee emp : manager.getAllEmployees()) {
            if (emp.getAvailability().isAvailable(shiftDate)) {
                allAvailableEmployees.add(emp);
            }
        }

        assignEmployeesToShiftRoles(scanner, allAvailableEmployees, assignedEmployeesToShift);

        if (assignedEmployeesToShift.isEmpty()) {
            System.out.println("No employees assigned to the shift. Shift creation cancelled.");
            return;
        }

        if (Shift_Manager.createShift(
                IdGenerator.getNextIdShift(),
                shiftDate,
                shiftType,
                manager,
                assignedEmployeesToShift,
                allAvailableEmployees
        ) == Status.Success) {
            System.out.println("Shift created successfully.");
        } else {
            System.out.println("Failed to create shift.");
        }
    }


    // main method to assign employees to shift roles
    // it will iterate over all roles and take input from the user
    // to select employees for each role
    private static void assignEmployeesToShiftRoles(Scanner scanner, List<Employee> allAvailableEmployees, List<Employee> assignedEmployeesToShift) {
        for (Map.Entry<String, Integer> entry : Shift.getRequiredRoles().entrySet()) {
            String role = entry.getKey();
            int requiredCount = entry.getValue();
            List<Employee> availableForRole = filterAvailableEmployeesByRole(allAvailableEmployees, role);
            selectEmployeesForRole(scanner, role, requiredCount, availableForRole, assignedEmployeesToShift);
        }
    }

    // iterate over all employees and check if the employee has corresponding role
    // if yes add to the list
    private static List<Employee> filterAvailableEmployeesByRole(List<Employee> allEmployees, String role) {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : allEmployees) {
            if (emp.getRolesList().stream().anyMatch(r -> r.getRole().equals(role))) {
                result.add(emp);
            }
        }
        return result;
    }

    // This method will be used to select employees for a specific role
    // It will display the available employees and allow the user to select them
    private static void selectEmployeesForRole(Scanner scanner, String role, int requiredCount, List<Employee> availableEmployees, List<Employee> assignedEmployees) {
        int selectedCount = 0;
        System.out.println("\nFor " + role + " needed: " + requiredCount + " employees");

        if (availableEmployees.isEmpty())
        {
            System.out.println("No available employees for the role: " + role);
            System.out.println("You should recruit employees for this role.");
        }

        while (selectedCount < requiredCount && !availableEmployees.isEmpty()) {
            printAvailableEmployeesWithIndex(availableEmployees);
            System.out.println("0. Skip assigning remaining employees for this role");
            System.out.println("Please select the corresponding number to the employee you wish to add.");
            System.out.println("Example: 1. Employee ID: 12345 - John Doe -> select 1");
            System.out.print("Enter your choice: ");

            int choice = getValidIntChoice(scanner, 0, availableEmployees.size());

            if (choice == 0) {
                System.out.println("Skipping remaining selections for role: " + role);
                break;
            }
            // subtract 1 from choice to get the index (starts from 0)
            Employee selectedEmployee = availableEmployees.get(choice - 1);

            if (assignedEmployees.contains(selectedEmployee)) {
                System.out.println("Employee already assigned. Choose a different one.");
                continue;
            }

            assignedEmployees.add(selectedEmployee);
            availableEmployees.remove(selectedEmployee);
            selectedCount++;

            if (selectedCount >= requiredCount) {
                System.out.println("\nYou have selected enough employees for the role: " + role);
            } else {
                System.out.println("\nYou need to select more " + (requiredCount - selectedCount) + " employees for the role: " + role);
            }
        }
    }

    // This method will print the available employees with their index
    // employees are selected to print based on the role
    // it will be used in the selectEmployeesForRole method
    private static void printAvailableEmployeesWithIndex(List<Employee> employees) {
        System.out.println("Available employees:");
        for (int i = 0; i < employees.size(); i++) {
            Employee emp = employees.get(i);
            System.out.println((i + 1) + ". Employee ID: " + emp.getEmployeeID() + " - " + emp.getName());
        }
    }

    private static int getValidIntChoice(Scanner scanner, int from, int to) {
        while (true) {
            try {
                int choice = scanner.nextInt();
                if (choice >= from && choice <= to) {
                    return choice;
                } else {
                    System.out.printf("Invalid choice. Please enter a number between %d and %d.%n", from, to);
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // clear invalid input
            }
        }
    }

    //case 2 in switch case printMenu_Manager
    private static void removeEmployeeFromShift(Employee employee) {
        Scanner scanner = new Scanner(System.in);
        String employeeId;
        Employee toRemove;

        try {
            System.out.println("Please enter the ID of the employee you want to remove from the shift:");
            employeeId = scanner.nextLine();
            toRemove = EmployeesDB.findEmployeeById(employeeId);


            if (toRemove == null) {
                System.out.println("Employee with ID " + employeeId + " not found.");
                return;
            } else if (employee.getEmployeeID().equals(toRemove.getEmployeeID())) {
                System.out.println("you cant remove yourself");
                return;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while searching for the employee.");
            return;
        }

        int shiftsFound = 0;
        String shiftId = null;

        for (Shift shift : ShiftsDB.getUpcomingShifts()) {
            if (shift.getAssignedEmployees().stream().anyMatch(emp -> emp.getEmployeeID().equals(employeeId))) {
                System.out.println(shift.getShiftID() + " - " + shift.getShiftDate() + " - " + shift.getShiftType());
                shiftsFound++;
                shiftId = shift.getShiftID();
            }
        }

        if (shiftsFound == 0) {
            System.out.println("Employee with ID " + employeeId + " not found in any shifts.");
            return;
        }

        if (shiftsFound == 1) {
            Shift shift = findShiftById(shiftId);
            if (shift != null) {
                shift.removeAssignedEmployee(toRemove);
                System.out.println("Employee with ID " + employeeId + " has been removed from the shift.");
            }
        } else {
            System.out.println("Multiple shifts found for employee with ID " + employeeId + ". Please specify the shift ID:");
            String shiftIdToRemoveFrom = scanner.nextLine();

            Shift shift = findShiftById(shiftIdToRemoveFrom);
            if (shift != null) {
                if (Shift_Manager.removeEmployeeFromShift(toRemove, shift) == Status.Success) {
                    System.out.println("Employee with ID " + employeeId + " has been removed from the shift.");
                } else {
                    System.out.println("Failed to remove employee from the shift.");
                }
            }
        }
    }

    //case 3 in switch case printMenu_Manager
    public static void addEmployeeToShift() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Employee ID you want to assign to a shift:");
        String empId = scanner.nextLine().trim();
        Employee employeeToAssign = EmployeesDB.findEmployeeById(empId);

        if (employeeToAssign == null) {
            System.out.println("Employee with ID " + empId + " does not exist.");
            return;
        }

        System.out.println("Enter the Shift ID to assign the employee to:");
        String shiftId = scanner.nextLine().trim();
        Shift shift = findShiftById(shiftId);

        if (shift == null) {
            System.out.println("Shift with ID " + shiftId + " does not exist.");
            return;
        }

        // Check if employee is available for the shift date
        if (!employeeToAssign.getAvailability().isAvailable(shift.getShiftDate())) {
            System.out.println("Employee " + employeeToAssign.getName() + " is not available for this shift date.");
            return;
        }

        // Check if employee has a role required for the shift
        boolean hasRequiredRole = false;
        for (Map.Entry<String, Integer> entry : Shift.getRequiredRoles().entrySet()) {
            String role = entry.getKey();
            if (employeeToAssign.getRolesList().stream().anyMatch(r -> r.getRole().equals(role))) {
                hasRequiredRole = true;
                break;
            }
        }

        if (!hasRequiredRole) {
            System.out.println("Employee " + employeeToAssign.getName() + " does not have any of the required roles for this shift.");
            return;
        }

        if (Shift_Manager.addEmployeeToShift(employeeToAssign, shift) == Status.Success) {
            System.out.println("Employee " + employeeToAssign.getName() + " added to shift " + shiftId);
        } else {
            System.out.println("Failed to add employee to the shift.");
        }
    }

    //case 4 in switch case printMenu_Manager
    private static void promoteEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Employee ID you want to promote:");
        String empId = scanner.nextLine().trim();
        Employee employeeToPromote = EmployeesDB.findEmployeeById(empId);

        if (employeeToPromote == null) {
            System.out.println("Employee with ID " + empId + " does not exist.");
            return;
        }
        if (employeeToPromote.getRolesList().stream().anyMatch(r -> r instanceof Shift_Manager  ||r instanceof HR_Lead)) {
            System.out.println("Cannot promote to equal or higher roles.");
            return;
        }

        // Display available roles
        System.out.println("Choose a role to promote the employee to:");
        System.out.println("1. Cashier");
        System.out.println("2. Shift Manager");
        System.out.println("3. HR Lead");
        System.out.println("4. Stocker");
        System.out.println("5. Security");
        System.out.println("6. Cleaner");

        int choice = getValidIntChoice(scanner, 1, 6);
        scanner.nextLine(); // Clear the buffer

        ARole roleToAdd;
        String roleName;

        // Use switch case to handle role selection
        switch (choice) {
            case 1:
                roleToAdd = new Cashier(IdGenerator.getNextIdCashier());
                roleName = "Cashier";
                break;
            case 2:
                roleToAdd = new Shift_Manager(IdGenerator.getNextIdManager());
                roleName = "Shift Manager";
                break;
            case 3:
                roleToAdd = new HR_Lead(IdGenerator.getNextIdHR());
                roleName = "HR Lead";
                break;
            case 4:
                roleToAdd = new Stocker(IdGenerator.getNextIdStocker());
                roleName = "Stocker";
                break;
            case 5:
                roleToAdd = new Security(IdGenerator.getNextIdSecurity());
                roleName = "Security";
                break;
            case 6:
                roleToAdd = new Cleaner(IdGenerator.getNextIdCleaner());
                roleName = "Cleaner";
                break;
            default:
                System.out.println("Invalid choice. Promotion cancelled.");
                return;
        }


        if (Shift_Manager.promote_Employee(employeeToPromote, roleToAdd) == Status.Success){
            System.out.println("Employee " + employeeToPromote.getName() + " promoted to " + roleName);
        } else {
            System.out.println("Employee " + employeeToPromote.getName() + " already has the role " + roleName);
        }
    }
}
