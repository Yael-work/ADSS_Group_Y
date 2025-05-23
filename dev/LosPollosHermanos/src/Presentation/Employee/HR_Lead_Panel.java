package Presentation.Employee;

import Auxiliary.*;
import Auxiliary.Enums.Domain.Status;
import Auxiliary.Enums.Models.AvailabilityStatus;
import Domain.Employees.*;
import Models.DBModels.Employees.*;
import Auxiliary.sharedFunctions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HR_Lead_Panel {

    private static void printMenu_HR() {
        System.out.println("\nWelcome to the HR Panel!\n");
        System.out.println("Please choose an action (1,2,3,4,5,6 or 7):");
        System.out.println("1. Terminate Employee from System");
        System.out.println("2. Add New Employee to System");
        System.out.println("3. Set Shift Hours");
        System.out.println("4. Update Required Roles to Shift");
        System.out.println("5. Set All Required Shift Roles");
        System.out.println("6. Update Employee's information");
        System.out.println("7. Going back to General Employee panel");
    }

    public static void moveToPanel_HR(Employee HRLead) {
        int choice;
        do {
            printMenu_HR();
            Scanner scanner = new Scanner(System.in);

            if (scanner.hasNextInt())
                choice = scanner.nextInt();
            else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    if (Terminate_Employee_from_System(HRLead) == Status.Success) {
                        System.out.println("Employee terminated successfully.");
                    } else {
                        System.out.println("Failed to terminate employee.");
                    }
                    break;
                case 2:
                    if (Add_Employee_to_System() == Status.Success) {
                        System.out.println("Employee added successfully.");
                    } else {
                        System.out.println("Failed to add employee.");
                    }
                    break;
                case 3:
                    if (Set_Shift_Hours() == Status.Success) {
                        System.out.println("Shift hours set successfully.");
                    } else {
                        System.out.println("Failed to set shift hours.");
                    }
                    break;
                case 4:
                    if (Update_Required_Roles_to_Shift() == Status.Success) {
                        System.out.println("Required roles updated successfully.");
                    } else {
                        System.out.println("Failed to update required roles.");
                    }
                    break;
                case 5:
                    if (set_required_roles_to_shift() == Status.Success) {
                        System.out.println("Required roles set successfully.");
                    } else {
                        System.out.println("Failed to set required roles.");
                    }
                    break;
                case 6:
                    if (Update_Employee_Information() == Status.Success) {
                        System.out.println("Employee information updated successfully.");
                    } else {
                        System.out.println("Failed to update employee information.");
                    }
                    break;
                case 7:
                    System.out.println("Going back to General Employee panel");
                    return; // Exit the panel
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }while (true);

    }

    public static Status Terminate_Employee_from_System(Employee HRLead)
    {
        System.out.println("Please Enter the ID of the Employee to terminate: ");
        Scanner scanner = new Scanner(System.in);
        String employeeID = scanner.nextLine();

        if (!EmployeeManagerCLS.doesEmployeeExist(employeeID)) {
            System.out.println("Employee not found.");
            return Status.Failure;
        }
        else if (employeeID.equals(HRLead.getEmployeeID())) {
            System.out.println("You cannot terminate yourself.");
            return Status.Failure;
        }

        return HR_Lead.Terminate_Employee_from_System(employeeID);
        // TODO: Remove employee from futur shifts
    }

    public static Status Add_Employee_to_System() {
        Scanner scanner = new Scanner(System.in);

        String employeeID = promptForEmployeeID(scanner);
        if (EmployeeManagerCLS.doesEmployeeExist(employeeID)) {
            System.out.println("Employee with this ID already exists.");
            return Status.Failure;
        }
        String employeeName = promptForEmployeeName(scanner);
        double employeeSalary = promptForSalary(scanner);

        int choice=0;
        do {
            System.out.println("Please choose Employee's role number:\n" +
                    "1. Manager\n2. HR Lead\n3. Cashier\n4. Cleaner\n5. Stocker\n6. Security");

            if (scanner.hasNextInt())
                choice = scanner.nextInt();
            else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
                continue;
            }

            if (choice <0 || choice > 6)
                System.out.println("Invalid choice. Please select a number from 1 to 6.");

        } while (choice <0 || choice > 6);

        ARole role = sharedFunctions.promptForRole(choice);

        List<ARole> rolesList = new ArrayList<>();
        rolesList.add(role);

        BankAccount employeeAccount = promptForBankAccount(scanner);
        Contract employeeContract = promptForContract(scanner);
        LocalDate recruitment_Date = LocalDate.now();


        if (employeeAccount == null || employeeContract == null) { return Status.Failure; }

        return HR_Lead.Add_Employee_to_System(employeeName, employeeSalary, employeeID, recruitment_Date,  rolesList, employeeAccount, employeeContract );
    }

    private static String promptForEmployeeID(Scanner scanner) {
        System.out.println("Please Enter Employee's ID: ");
        return scanner.nextLine();
    }

    private static String promptForEmployeeName(Scanner scanner) {
        System.out.println("Please Enter Employee's name: ");
        return scanner.nextLine();
    }

    private static double promptForSalary(Scanner scanner) {
        double salary;
        do {
            System.out.println("Please Enter Employee's salary: ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.next();
            }
            salary = scanner.nextDouble();
            if (salary < 0) {
                System.out.println("Invalid salary. Should be a positive number.");
            }
        } while (salary < 0);
        scanner.nextLine(); // clear buffer
        return salary;
    }



    private static BankAccount promptForBankAccount(Scanner scanner) {
        System.out.println("Please Enter Employee's bank information: ");
        scanner.nextLine(); // Clear the buffer
        System.out.print("Bank name: ");
        String bankName = scanner.nextLine();
        System.out.print("Bank account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Bank branch number: ");
        String branch = scanner.nextLine();

        return new BankAccount(bankName, accountNumber, branch);
    }

    private static Contract promptForContract(Scanner scanner) {
        System.out.println("Please Enter Employee's Contract information:");

        int pension = promptForPositiveInt(scanner, "Pension Percentage: ");
        int sickDays = promptForPositiveInt(scanner, "Sick days number: ");
        int vacation = promptForPositiveInt(scanner, "Vacation days number: ");
        int minHours = promptForPositiveInt(scanner, "Min monthly hours: ");

        // TODO: There's a mismatch between the Contract constructor definition (5 params) and its usage (4 params)
        // This needs to be addressed in a separate task
        return new Contract(IdGenerator.getNextIdContract(),pension, sickDays, vacation, minHours);
    }

    private static int promptForPositiveInt(Scanner scanner, String message) {
        int value;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value >= 0) {
                    scanner.nextLine(); // consume leftover newline
                    break;
                } else {
                    System.out.println("Value must be 0 or more. Try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a whole number.");
                scanner.next(); // clear invalid input
            }
        }
        return value;
    }

    public static Status Set_Shift_Hours() {
        Scanner scanner = new Scanner(System.in);
        String shiftType = sharedFunctions.getShiftTypeFromUser(scanner);
        System.out.println("Start Time: ");
        LocalTime startTime = sharedFunctions.parseTimeFromUser(scanner);
        System.out.println("End Time: ");
        LocalTime endTime = sharedFunctions.parseTimeFromUser(scanner);
        return HR_Lead.Set_Shift_Hours(shiftType, startTime, endTime);
        // TODO CHECK IF THE NEW SHIFT WITH NEW HOURS AND THAT HISTOTY NOT UPDATED
    }

    public static Status set_required_roles_to_shift() {
        Scanner scanner = new Scanner(System.in);
        int choice=0;
        boolean continueLoop;
        do {
            do {
                continueLoop = false;
                System.out.println("Please choose the number of ONE Employee's role you want in the shifts from the optional roles list:");
                sharedFunctions.DisplayAvailableRoles();

                if (scanner.hasNextInt())
                    choice = scanner.nextInt();
                else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear the invalid input
                    continueLoop = true;
                    continue;
                }

                if (choice <1 || choice > 6)
                    System.out.println("Invalid choice. Please select a number from 1 to 6.");

            } while (continueLoop);

            String role = sharedFunctions.promptForRole(choice).getRole();
            int amount = promptForPositiveInt(scanner, "Please Enter the wanted amount of this role: ");
            if (amount == 0 )
                System.out.println("You cannot set the amount to 0\n");
            else
                HR_Lead.Update_Required_Role_to_Shift(1, amount, role);

            String answer;
            do {
                System.out.println("Do you want to add or update another role? (yes/no)");
                answer = scanner.nextLine();
                if (answer.equals("yes")) {
                    continueLoop = true;
                } else if (answer.equals("no")) {
                    continueLoop = false;
                } else {
                    System.out.println("Invalid answer. Please enter 'yes' or 'no'.");
                }
            } while (!answer.equals("yes") && !answer.equals("no"));

        } while (continueLoop);

        return Status.Success;
    }



    public static Status Update_Required_Roles_to_Shift() {
        Scanner scanner = new Scanner(System.in);

        int action=0;
        do {
            System.out.println("which action do you want to do? (1 OR 2)\n" +
                    "1. Add new role OR Update the amount of a role\n*You can increase or decrease the number of employees in the role.*\n" +
                    "2. Remove existing role");

            if (scanner.hasNextInt())
                action = scanner.nextInt();
            else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
                continue;
            }

            if (action != 1 && action != 2)
                System.out.println("Invalid action. Please choose 1 or 2.");

        } while (action != 1 && action != 2);


        int choice=0;
        boolean continueLoop;
        do {
            System.out.println("Please choose number of a role:\n");
            sharedFunctions.DisplayAvailableRoles();


            if (scanner.hasNextInt())
            {
                choice = scanner.nextInt();
                continueLoop = false;
            }
            else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
                continueLoop = true;
                continue;
            }

            if (choice <1 || choice > 6)
            {
                System.out.println("Invalid choice. Please select a number from 1 to 6.");
                continueLoop = true;
            }

        } while (continueLoop);

        String role = sharedFunctions.promptForRole(choice).getRole();

        if (action == 2 && !Shift.REQUIRED_ROLES_FOR_SHIFT.containsKey(role)) {
            System.out.println("This role does not exist in the shift.\nNo role removed.");
            return Status.Failure;
        }

        if (action == 2)
            HR_Lead.Update_Required_Role_to_Shift(action, 0, role);
        else
        {
            int amount = promptForPositiveInt(scanner, "Please Enter the wanted amount of this role: ");
            if (amount == 0 )
            {
                System.out.println("You cannot set the amount to 0, Choose to remove the role\nNo role added.");
                return Status.Failure;
            }
            HR_Lead.Update_Required_Role_to_Shift(action, amount, role);
        }

        return Status.Success;
    }

    public static Status Update_Employee_Information() { //TODO code need to be evalutated and changed accordingaly
        Scanner scanner = new Scanner(System.in);

        // Ask for employee ID
        System.out.println("Please Enter the ID of the Employee to update: ");
        String employeeID = scanner.nextLine();

        // Find employee by ID
        Employee employee = EmployeeManagerCLS.getEmployeeById(employeeID);
        if (employee == null) {
            System.out.println("Employee not found.");
            return Status.Failure;
        }

        // Display employee information
        System.out.println("Current employee information:");
        System.out.println(employee.toString());

        // Menu for choosing what to update
        int choice = 0;
        do {
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Salary");
            System.out.println("2. Add Role");
            System.out.println("3. Availability");
            System.out.println("4. Go back");

            choice = sharedFunctions.getValidIntChoice(scanner, 1, 4);

            switch (choice) {
                case 1: // Update salary
                    double newSalary = promptForSalary(scanner);
                    employee.setSalary(newSalary);
                    System.out.println("Salary updated successfully.");
                    return Status.Success;

                case 2: // Add role
                    System.out.println("Please choose a role to add:");
                    sharedFunctions.DisplayAvailableRoles();
                    int roleChoice = sharedFunctions.getValidIntChoice(scanner, 1, 6);

                    ARole newRole = sharedFunctions.promptForRole(roleChoice);
                    EmployeeManagerCLS.addNewRoleToList(newRole, employee);
                    System.out.println("Role added successfully.");
                    return Status.Success;

                case 3: // Update availability
                    Availability newAvailability = promptForAvailability(scanner);
                    employee.setAvailability(newAvailability);
                    System.out.println("Availability updated successfully.");
                    return Status.Success;

                case 4: // Go back
                    System.out.println("No changes made.");
                    return Status.Failure;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (true);
    }

    private static Availability promptForAvailability(Scanner scanner) {
        System.out.println("Please set availability for each day of the week:");
        System.out.println("Options: 1 = Day, 2 = Night, 3 = Both, 4 = Unavailable");

        AvailabilityStatus sunday = promptForDayAvailability(scanner, "Sunday");
        AvailabilityStatus monday = promptForDayAvailability(scanner, "Monday");
        AvailabilityStatus tuesday = promptForDayAvailability(scanner, "Tuesday");
        AvailabilityStatus wednesday = promptForDayAvailability(scanner, "Wednesday");
        AvailabilityStatus thursday = promptForDayAvailability(scanner, "Thursday");

        return new Availability(sunday, monday, tuesday, wednesday, thursday);
    }

    private static AvailabilityStatus promptForDayAvailability(Scanner scanner, String day) {
        int choice;
        do {
            System.out.println("Enter availability for " + day + " (1-4):");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
                choice = 0;
                continue;
            }

            switch (choice) {
                case 1:
                    return AvailabilityStatus.Day;
                case 2:
                    return AvailabilityStatus.Night;
                case 3:
                    return AvailabilityStatus.Both;
                case 4:
                    return AvailabilityStatus.Unavailable;
                default:
                    System.out.println("Invalid choice. Please select a number from 1 to 4.");
            }
        } while (true);
    }

}
