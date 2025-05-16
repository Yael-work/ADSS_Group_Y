package Presentation.Employee;

import DB.WorkersDB.EmployeesDB;
import DB.WorkersDB.ShiftsDB;
import Models.DBModels.Employees.Employee;
import Models.DBModels.Employees.Shift;

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
            //TODO DEAL WITH NO NUMBER INPUT
            continueLoop = false;
            System.out.println("\nPlease choose an action (1 or 2):");
            System.out.println("1. Enter to your Employee Role");
            System.out.println("2. Exit System");

            if (scanner.hasNextInt())
                choice = scanner.nextInt();
            else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
                continueLoop = true;
                continue;
            }

            switch (choice){
                case 1:
                    scanner.nextLine(); // Clear the buffer
                    System.out.println("Please Enter Your ID: ");
                    String employeeID = scanner.nextLine();// Read user input
                    enteredEmployee = EmployeesDB.findEmployeeById(employeeID);
                    if (enteredEmployee == null) {
                        System.out.println("Invalid Employee ID - There is no employee with the following ID.\nPlease try again.");
                        continueLoop = true;
                    }
                    break;
                case 2:
                    System.out.println("Exiting the system...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continueLoop = true;
            }

        } while (continueLoop);

        General_Employee_Panel.moveToPanel_GeneralEmployee(enteredEmployee);
    }




    public static void EmployeesPresentationHandler() {
        System.out.println("Loading mock data...");
        Employee.createEmployeesDB();
        // Load mock ShiftsDB data
        Shift.createShiftsDB();
        // Move past shifts to pastShiftsDB before any employee interaction
        ShiftsDB.moveShiftToPast();
        // Clean up old shifts (older than 7 years) on application startup
        ShiftsDB.removeOldPastShifts();

        do {
            printLoginPanel();
        } while (true);
    }
}
