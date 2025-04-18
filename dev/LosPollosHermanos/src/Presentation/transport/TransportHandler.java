package Presentation.transport;

import Auxiliary.Helper;
import Domain.transport.BranchesManager;
import Models.DBModels.transport.Branch;
import Models.DTOs.StockItem;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TransportHandler {

    /**
     * This method is used to search for stock items based on a search string and branch ID.
     *
     * @param searchString The search string to filter stock items.
     * @param branchId     The ID of the branch to filter stock items.
     * @return A list of relevant stock items.
     */
    private static ArrayList<StockItem> mockSearchAndReturnRelevantStockItems(String searchString, int branchId) {
        // Mock implementation to simulate searching and returning relevant stock items
        ArrayList<StockItem> stockItems = new ArrayList<>();
        stockItems.add(new StockItem(1, "Item1", branchId, 10));
        stockItems.add(new StockItem(2, "Item2", branchId, 20));
        stockItems.add(new StockItem(3, "Item3", branchId, 30));
        stockItems.add(new StockItem(4, "Item4", branchId, 40));
        stockItems.add(new StockItem(5, "Item5", branchId, 50));
        return stockItems;
    }


    private static void createTransport() {
        Helper.println("Transport Creation:");
        Helper.println(String.format("Please enter the planned date and time of transport (%s):",
                Helper.DefaultDateTimeFormat));
        String dateTimeStr = Helper.getUserInputString();
        LocalDateTime dateTime = Helper.stringToDateTime(dateTimeStr);
        while (dateTime == null) {
            Helper.print("Invalid date and time format. Please try again: ");
            dateTimeStr = Helper.getUserInputString();
            dateTime = Helper.stringToDateTime(dateTimeStr);
        }
        ArrayList<Branch> branches = BranchesManager.getExistingBranches();
        if (branches == null || branches.isEmpty()) {
            Helper.println("No branches available. Please create a branch first.");
            return;
        }
        for (Branch branch : branches) {
            Helper.println(String.format("Branch ID: %d, Branch Name: %s", branch.getId(), branch.getSiteName()));
        }
        String userInput;
        do {
            Helper.print("Select the current branch to create transport for, " +
                    "or write 'continue' to proceed with the transport creation: ");
            userInput = Helper.getUserInputString();
            if (userInput != null && !userInput.equalsIgnoreCase("continue")) {
                int branchId;
                try {
                    branchId = Integer.parseInt(userInput);
                } catch (NumberFormatException e) {
                    Helper.println("Invalid input. Please enter a valid branch ID or 'continue'.");
                    continue;
                }
                if (!Helper.isValidId(branches, branchId)) {
                    Helper.println("Invalid branch ID. Please try again.");
                    continue;
                }
                Branch selectedBranch = branches.stream().filter(branch -> branch.getId() == branchId)
                        .findFirst().orElse(null);
                if (selectedBranch == null) {
                    Helper.println("Branch not found. Please try again.");
                    continue;
                }
                Helper.println("You have selected branch: " + selectedBranch.getSiteName());

                Helper.print("Please enter the search string for stock items: ");
                String searchString = Helper.getUserInputString();
                ArrayList<StockItem> stockItems = mockSearchAndReturnRelevantStockItems(searchString, branchId);
            }
        } while (userInput == null || !userInput.equalsIgnoreCase("continue"));

    }

    /**
     * This method is used to handle the transport management menu.
     */
    public static void handleTransports() {
        boolean exit = false;
        while (!exit) {
            int choice;
            Helper.println("Manage Transports:");
            Helper.println("1. Show all transports");
            Helper.println("2. Show specific detailed transport by ID");
            Helper.println("3. Print transport drives for driver - by Transport ID");
            Helper.println("4. Print the next drive for driver");
            Helper.println("5. Delete transport by ID");
            Helper.println("6. Create transport");
            Helper.println("7. Start transport");
            Helper.println("8. Visit source");
            Helper.println("9. Visit destination");
            Helper.println("10. Edit Unstarted Transport");
            Helper.println("12. Back to main menu");

            do {
                Helper.print("Please choose an option: ");
                choice = Helper.getUserInputInteger();
            } while (choice == Helper.UndefinedUserInput);

            switch (choice) {
                case 1:
                    // Show all transports
                    break;
                case 2:
                    // Show specific detailed transport by ID
                    break;

                case 3:
                    // Print transport drives for driver - by Transport ID
                    break;
                case 4:
                    // Print the next drive for driver
                    break;
                case 5:
                    // Delete transport by ID
                    break;
                case 6:
                    createTransport();
                    break;
                case 7:
                    // Start transport
                    break;
                case 8:
                    // Visit source
                    break;
                case 9:
                    // Visit destination
                    break;
                case 10:
                    // Edit Unstarted Transport
                    break;
                case 11:
                    // Show all transports
                    break;
                case 12:
                    exit = true;
                    break;
                default:
                    Helper.println("Invalid choice. Please try again.");
            }
        }
    }
}
