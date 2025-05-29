package Presentation.Employee;

import Auxiliary.sharedFunctions;
import DB.WorkersDB.ShiftsDB;
import Domain.Employees.EmployeeManagerCLS;
import Domain.Employees.PastShiftsManagerCLS;
import Domain.Employees.UpcomingShiftsManagerCLS;
import Models.DBModels.Employees.Employee;

// Following imports are only for mock data upload-----
import DB.implementations.sqlite.employees.SqlitePastShiftsController;
import DB.implementations.MockFactory.MockEmployeeData;
import DB.implementations.MockFactory.MockShiftsData;
import DB.implementations.sqlite.employees.SqliteEmployeesController;
import DB.implementations.sqlite.employees.SqliteUpcomingShiftsController;
import Models.DBModels.Employees.Employee;
import Models.DBModels.Employees.Shift;
//-----------------------

import java.util.List;
import java.util.Scanner;
import java.util.Scanner;

public class System_Login_Panel {

    private static void printLoginPanel() {
        Scanner scanner = new Scanner(System.in);  // Create Scanner object

        System.out.println("\nWelcome to the Employees Management System!\n");
        System.out.println("You are on Login panel!");

        boolean continueLoop;
        int choice;
        Employee enteredEmployee = null;
        do {
            continueLoop = false;
            System.out.println("\nPlease choose an action (1 or 2):");
            System.out.println("1. Enter to your Employee Role");
            System.out.println("2. Exit System");

            choice = sharedFunctions.getValidIntChoice(scanner, 1, 2);

            switch (choice) {
                case 1:
                    scanner.nextLine(); // Clear the buffer
                    System.out.println("Please Enter Your ID: ");
                    String employeeID = scanner.nextLine();// Read user input
                    enteredEmployee = EmployeeManagerCLS.getEmployeeById(employeeID);
                    if (enteredEmployee == null) {
                        System.out.println("Invalid Employee ID - There is no employee with the following ID.\nPlease try again.");
                        continueLoop = true;
                    }
                    break;
                case 2:
                    System.out.println("Exiting the system...");
                    continueLoop = false;
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continueLoop = true;
            }
        } while (continueLoop);

        General_Employee_Panel.moveToPanel_GeneralEmployee(enteredEmployee);
    }


     public static void EmployeesPresentationHandler() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Would you like to load mock data? (yes/no)");
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("yes")) {
                System.out.println("Loading mock data...");

                try {
                    // Set test mode to use test database
                    Auxiliary.GlobalConfig.setTestMode(true);

                    // Force Hibernate to recreate the session factory
                    DB.util.HibernateUtil.invalidateSessionFactory();

                    // Get a new session to ensure schema is created
                    try (org.hibernate.Session session = DB.util.HibernateUtil.getSessionFactory().openSession()) {
                        //System.out.println("Database connection established");

                        // Clear existing data to avoid unique constraint violations
                        session.beginTransaction();
                        //System.out.println("Clearing existing mock data...");
                        session.createNativeQuery("DELETE FROM shift_assigned_employees").executeUpdate();
                        session.createNativeQuery("DELETE FROM shift_required_roles").executeUpdate();
                        session.createNativeQuery("DELETE FROM Shifts").executeUpdate();
                        session.createNativeQuery("DELETE FROM employee_roles").executeUpdate();
                        session.createNativeQuery("DELETE FROM Roles").executeUpdate();
                        session.createNativeQuery("DELETE FROM Employees").executeUpdate();
                        session.createNativeQuery("DELETE FROM Availabilitys").executeUpdate();
                        session.createNativeQuery("DELETE FROM BankAccounts").executeUpdate();
                        session.createNativeQuery("DELETE FROM Contracts").executeUpdate();
                        session.getTransaction().commit();
                        //System.out.println("Existing data cleared successfully");

                        // Begin transaction for adding employees
                        session.beginTransaction();

                        // Create and add mock employees
                        List<Employee> employees = MockEmployeeData.createMockEmployees();
                        //System.out.println("Created " + employees.size() + " mock employees");

                        for (Employee emp : employees) {
                            try {
                                // Use merge instead of persist to handle detached entities
                                session.merge(emp);
                                //System.out.println("Added employee: " + emp.getEmployeeID());
                            } catch (Exception e) {
                                System.out.println("Error adding employee " + emp.getEmployeeID() + ": " + e.getMessage());
                            }
                        }

                        // Commit the transaction
                        session.getTransaction().commit();
                        //System.out.println("Employee transaction committed");

                        // Begin new transaction for shifts
                        session.beginTransaction();

                        // Fetch the persisted employees from database to use in shifts
                        List<Employee> persistedEmployees = session.createQuery("FROM Employee", Employee.class).getResultList();
                        //System.out.println("Fetched " + persistedEmployees.size() + " persisted employees");

                        // Create and add mock shifts using persisted employees
                        List<Shift> shifts = MockShiftsData.createUpcomingShiftsMockDataWithEmployees(persistedEmployees);
                        //System.out.println("Created " + shifts.size() + " mock shifts");

                        for (Shift shift : shifts) {
                            try {
                                // Use merge instead of persist to handle detached entities
                                session.merge(shift);
                                // System.out.println("Added shift: " + shift.getShiftID());
                            } catch (Exception e) {
                                System.out.println("Error adding shift " + shift.getShiftID() + ": " + e.getMessage());
                            }
                        }

                        // Commit the transaction
                        session.getTransaction().commit();
                        //System.out.println("Shift transaction committed");
                    }

                    // Reset to production mode
                    // Auxiliary.GlobalConfig.setTestMode(false); // Keep test mode to use mock data
                    //System.out.println("Mock data loading completed");

                } catch (Exception e) {
                    System.out.println("Error in mock data process: " + e.getMessage());
                    e.printStackTrace();
                    // Reset to production mode
                    // Auxiliary.GlobalConfig.setTestMode(false); // Keep test mode to use mock data
                }
                break;
            } else if (answer.equals("no")) {
                System.out.println("Skipping mock data loading.");
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
        printLoginPanel();
    }
}
