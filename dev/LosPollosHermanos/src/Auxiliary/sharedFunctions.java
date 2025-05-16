package Auxiliary;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.time.LocalTime;

public class sharedFunctions {

    public static LocalDate parseDate(Scanner scanner, String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();

            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter date in format dd/MM/yyyy:");
            }
        }
    }

    public static LocalTime parseTimeFromUser(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        while (true) {
            System.out.println("Enter time in format HH:mm ,example: \"09:45\"");
            String input = scanner.nextLine().trim();
            try {
                return LocalTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please try again.");
            }
        }
    }

    public static String getShiftTypeFromUser(Scanner scanner) {
        System.out.println("Please enter the shift type (Day or Night):");
        String shiftType = scanner.nextLine().trim();

        while (!isValidShiftType(shiftType)) {
            System.out.println("Invalid input. Please enter either 'Day' or 'Night':");
            shiftType = scanner.nextLine().trim();
        }
        if (shiftType.equalsIgnoreCase("Day")) {
            return "Day";
        } else {
            return "Night";
        }
    }
    private static boolean isValidShiftType(String shiftType) {
        return shiftType.equalsIgnoreCase("Day") || shiftType.equalsIgnoreCase("Night");
    }

}