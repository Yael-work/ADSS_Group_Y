package Presentation.transport;

import Auxiliary.Helper;
import Domain.transport.BranchesManager;
import Domain.transport.TransportManager;
import Models.DBModels.transport.Branch;
import Models.DTOs.StockItem;
import Models.DTOs.transport.CreateNewTransportRoutesResult;

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
        String userInput;
        do {
            for (Branch branch : branches) {
                Helper.println(String.format("Branch ID: %d, Branch Name: %s", branch.getId(), branch.getSiteName()));
            }
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

                userInput = null;
                ArrayList<StockItem> selectedItems = new ArrayList<>();
                do {
                    Helper.print("Please enter the search string for stock items or 'continue' to proceed: ");
                    userInput = Helper.getUserInputString();
                    while (userInput == null || userInput.trim().isEmpty()) {
                        Helper.print("Invalid search string. Please try again: ");
                        userInput = Helper.getUserInputString();
                    }
                    if (userInput.equalsIgnoreCase("continue")) {
                        break;
                    }
                    ArrayList<StockItem> stockItems = mockSearchAndReturnRelevantStockItems(userInput, branchId);
                    for (StockItem stockItem : stockItems) {
                        Helper.println(String.format("Item ID: %d, Item Name: %s, Amount: %.2f",
                                stockItem.getItemId(), stockItem.getItemName(), stockItem.getAmount()));
                    }
                    Helper.print("Please select the relevant item you want to add to the transport by its ID," +
                            " or write 0 to skip this item: ");
                    int itemId = Helper.getUserInputInteger();
                    while (itemId == Helper.UndefinedUserInput) {
                        Helper.print("Invalid item ID. Please try again: ");
                        itemId = Helper.getUserInputInteger();
                    }
                    if (itemId == 0) {
                        Helper.println("Skipping this item.");
                    } else {
                        int finalItemId = itemId;
                        StockItem selectedItem = stockItems.stream().filter(item -> item.getItemId() == finalItemId)
                                .findFirst().orElse(null);
                        if (selectedItem != null) {
                            if (selectedItems.stream().anyMatch(item -> item.getItemId() == selectedItem.getItemId())) {
                                Helper.println("Item already selected. Please choose a different item.");
                            } else {
                                Helper.println("You have selected item: " + selectedItem.getItemName());
                                Helper.println("The current amount at the branch is: " + selectedItem.getAmount());
                                Helper.println("Please enter the amount you want to add to the transport: ");
                                double amount = Helper.getUserInputDouble();
                                while (amount <= 0) {
                                    Helper.print("Invalid amount. Please enter a valid amount: ");
                                    amount = Helper.getUserInputDouble();
                                }
                                selectedItem.setAmount(amount);
                                selectedItems.add(selectedItem);
                            }
                        } else {
                            Helper.println("Invalid item ID. Please try again.");
                        }
                    }
                }
                while (userInput.trim().isEmpty() || !userInput.equalsIgnoreCase("continue"));
                userInput = null;


                Helper.println("The items you selected for this transport are:");
                for (StockItem selectedItem : selectedItems) {
                    Helper.println(String.format("Item ID: %d, Item Name: %s, Amount: %.2f",
                            selectedItem.getItemId(), selectedItem.getItemName(), selectedItem.getAmount()));
                }
                CreateNewTransportRoutesResult routesCreationResult =
                        TransportManager.createNewTransportRoutes(branchId, selectedItems);
                switch (routesCreationResult.getResultEnum()) {
                    case Success -> {

                    }
                    case SomeItemsUnprovided -> {
                        Helper.println("Some items could not be provided by the suppliers.");
                        Helper.println("Please check the following items:");
                        for (StockItem unprovidedItem : routesCreationResult.getUnprovidedItems()) {
                            Helper.println(String.format("Item ID: %d, Item Name: %s, Amount: %.2f",
                                    unprovidedItem.getItemId(), unprovidedItem.getItemName(), unprovidedItem.getAmount()));
                        }
                        Helper.print("Would you like to proceed with the transport creation? (yes/no): ");
                        String proceedInput = Helper.getUserInputString();
                        while (proceedInput == null
                                || (!proceedInput.equalsIgnoreCase("yes")
                                && !proceedInput.equalsIgnoreCase("no"))) {
                            Helper.print("Invalid input. Please enter 'yes' or 'no': ");
                            proceedInput = Helper.getUserInputString();
                        }
                        if (proceedInput.equalsIgnoreCase("yes")) {
                            //todo handle success
                        } else {
                            Helper.println("Transport creation cancelled.");
                            return;
                        }
                    }
                    case UNDEFINED -> {
                        Helper.println("Failed to create transport routes.");
                        return;
                    }
                }
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
