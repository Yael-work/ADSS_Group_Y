package Presentation.Employee;

import Auxiliary.sharedFunctions;
import DB.WorkersDB.ShiftsDB;
import Domain.Employees.EmployeeManagerCLS;
import Models.DBModels.Employees.Employee;

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
        while (true) {
            System.out.println("Would you like to load mock data? (yes/no)");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("yes")) {
                System.out.println("Loading mock data...");
                Employee.createEmployeesDB();
                //Shift.createShiftsDB();
                // Move past shifts to pastShiftsDB before any employee interaction
                ShiftsDB.moveShiftToPast();
                // Clean up old shifts (older than 7 years) on application startup
                ShiftsDB.removeOldPastShifts();
                break;
            }
            if (answer.equals("no")) {
                System.out.println("Skipping mock data loading.");
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
        printLoginPanel();
    }
}
