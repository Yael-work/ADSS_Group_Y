package Presentation.transport;

import Auxiliary.Helper;
import Domain.stock.StockManager;
import Domain.transport.BranchesManager;
import Domain.transport.DriversManager;
import Domain.transport.TransportManager;
import Domain.transport.TrucksManager;
import Models.DBModels.transport.*;
import Models.DTOs.StockItem;
import Models.DTOs.transport.CreateNewTransportRoutesResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransportHandler {


    /**
     * This method is used to get the date and time for transport from the user.
     *
     * @return The planned date and time of transport.
     */
    private static LocalDateTime getDateForTransport() {
        Helper.println(String.format("Please enter the planned date and time of transport (%s):",
                Helper.DefaultDateTimeFormat));
        String dateTimeStr = Helper.getUserInputString();
        LocalDateTime dateTime = Helper.stringToDateTime(dateTimeStr);
        while (dateTime == null) {
            Helper.print("Invalid date and time format. Please try again: ");
            dateTimeStr = Helper.getUserInputString();
            dateTime = Helper.stringToDateTime(dateTimeStr);
        }
        return dateTime;
    }


    /**
     * This method is used to get the branch ID for transport based on user input.
     *
     * @param userInput        The user input string.
     * @param existingBranches The list of existing branches.
     * @return The branch ID if valid, otherwise undefined ID.
     */
    private static int getBranchIdForTransport(String userInput, List<Branch> existingBranches) {
        int branchId = Helper.UndefinedId;
        try {
            branchId = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            Helper.println("Invalid input. Please enter a valid branch ID or 'continue'.");
            return Helper.UndefinedId;
        }
        if (!Helper.isValidId(existingBranches, branchId)) {
            Helper.println("Invalid branch ID. Please try again.");
            return Helper.UndefinedId;
        }
        int finalBranchId = branchId;
        Branch selectedBranch = existingBranches.stream().filter(branch -> branch.getId() == finalBranchId)
                .findFirst().orElse(null);
        if (selectedBranch == null) {
            Helper.println("Branch not found. Please try again.");
            return Helper.UndefinedId;
        }
        Helper.println("You have selected branch: " + selectedBranch.getSiteName());
        return branchId;
    }


    /**
     * This method is used to get the stock items selected by the user for transport to a specific branch
     *
     * @param branchId The ID of the branch.
     * @return A list of selected stock items for transport.
     */
    private static ArrayList<StockItem> getStockItemsUserSelectionOfBranchForTransport(int branchId) {
        String userInput;
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
            ArrayList<StockItem> stockItems = StockManager.mockSearchAndReturnRelevantStockItems(userInput, branchId);
            for (StockItem stockItem : stockItems) {
                Helper.println(String.format("Item ID: %d, Item Name: %s, Amount: %.2f",
                        stockItem.getStockItemId(), stockItem.getItemName(), stockItem.getAmount()));
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
                StockItem selectedItem = stockItems.stream().filter(item -> item.getStockItemId() == finalItemId)
                        .findFirst().orElse(null);
                if (selectedItem != null) {
                    if (selectedItems.stream().anyMatch(item -> item.getStockItemId() == selectedItem.getStockItemId())) {
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
        return selectedItems;
    }


    /**
     * This method is used to perform the actual transport creation process.
     *
     * @param plannedDateTime           The planned date and time of transport.
     * @param createdTransportRoutesIds The list of created transport routes IDs.
     * @return True if the transport creation was successful, false otherwise.
     */
    private static boolean performActualTransportCreation(LocalDateTime plannedDateTime,
                                                          List<Integer> createdTransportRoutesIds) {
        if (createdTransportRoutesIds == null || createdTransportRoutesIds.isEmpty()) {
            Helper.println("No transport routes created. Cannot proceed with transport creation.");
            return false;
        }
        List<Truck> trucks = TransportManager.getAvailableTrucksByDate(plannedDateTime);
        boolean truckAndDriverSelectionSuccess = trucks.isEmpty();
        if (truckAndDriverSelectionSuccess) {
            Helper.println("No trucks available for the selected date and time.");
            //todo add support to edit the time right here and then search again!
            return false;
        }
        do {
            Helper.println(String.format("Select a truck for the transport (Only available trucks are visible for the" +
                    " date %s):", plannedDateTime));
            trucks.forEach(truck -> Helper.println(truck.toPrettyString()));
            Helper.print("Please enter the truck ID: ");
            int truckId = Helper.getUserInputInteger();
            while (truckId == Helper.UndefinedUserInput || !Helper.isValidId(trucks, truckId)) {
                Helper.print("Invalid truck ID. Please try again: ");
                truckId = Helper.getUserInputInteger();
            }
            Truck selectedTruck = TrucksManager.getTruckById(truckId);
            //todo - we need to calculate all the weight of the items linked to each route - and compare that total to the truck max weight!
            if (selectedTruck == null) {
                Helper.println("Truck not found. Please try again.");
                continue;
            } else {
                Helper.println("You have selected truck: " + selectedTruck.toPrettyString());
            }

            List<Driver> drivers = TransportManager.getAvailableDriversByDateAndLicense(plannedDateTime,
                    selectedTruck.getRequiredLicenseType());
            if (drivers.isEmpty()) {
                Helper.println("No drivers available for the selected date and time and license type of %s"
                        .formatted(selectedTruck.getRequiredLicenseType()));
                break;
            }
            Helper.println(String.format("Select a driver for the transport" +
                    "(Only available drivers with suitable license are visible for the" +
                    " date %s):", plannedDateTime));
            drivers.forEach(driver -> Helper.println(driver.toPrettyString()));
            Helper.print("Please enter the driver ID: ");
            int driverId = Helper.getUserInputInteger();
            while (driverId == Helper.UndefinedUserInput || !Helper.isValidId(drivers, driverId)) {
                Helper.print("Invalid driver ID. Please try again: ");
                driverId = Helper.getUserInputInteger();
            }
            Driver d = DriversManager.getDriverById(driverId);
            if (d == null) {
                Helper.println("Driver not found. Please try again.");
            } else {
                Helper.println("You have selected driver: " + d.toPrettyString());
                Helper.println("Generating transport...");
                int createdTransportId = TransportManager.createTransport(plannedDateTime, truckId,
                        driverId, createdTransportRoutesIds);
                Transport createdTransport = TransportManager.getTransportById(createdTransportId);
                if (createdTransport == null) {
                    Helper.println("Failed to create transport.");
                    return false;
                }
                Helper.println("You have created transport: " + createdTransport.toPrettyString());
                truckAndDriverSelectionSuccess = true;
            }
        } while (!truckAndDriverSelectionSuccess);
        return truckAndDriverSelectionSuccess;
    }


    /**
     * This method is the main routine for creating transport.
     * It handles the selection of branches, stock items,
     * and the creation of transport routes.
     */
    private static void createTransportMainRoutine() {
        List<Integer> createdTransportRoutesIds = new ArrayList<>();
        Helper.println("Transport Creation:");
        LocalDateTime dateTime = getDateForTransport();

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
                int branchId = getBranchIdForTransport(userInput, branches);
                if (branchId == Helper.UndefinedId) {
                    continue;
                }
                userInput = null;

                List<StockItem> selectedItems = getStockItemsUserSelectionOfBranchForTransport(branchId);
                Helper.println("The items you selected for this transport are:");
                for (StockItem selectedItem : selectedItems) {
                    Helper.println(String.format("Item ID: %d, Item Name: %s, Amount: %.2f",
                            selectedItem.getStockItemId(), selectedItem.getItemName(), selectedItem.getAmount()));
                }
                Helper.println("Generating routes from suppliers to branch...");
                CreateNewTransportRoutesResult routesCreationResult =
                        TransportManager.createNewTransportRoutes(branchId, selectedItems);
                switch (routesCreationResult.getResultEnum()) {
                    case Success -> {
                        Helper.println("Transport routes created successfully for branch " + branchId);
                        createdTransportRoutesIds.addAll(routesCreationResult.getCreatedTransportRoutesIds());
                    }
                    case SomeItemsUnprovided -> {
                        Helper.println("Some items could not be provided by the suppliers for this branch");
                        Helper.println("Please check the following items:");
                        for (StockItem unprovidedItem : routesCreationResult.getUnprovidedItems()) {
                            Helper.println(String.format("Item ID: %d, Item Name: %s, Amount: %.2f",
                                    unprovidedItem.getStockItemId(),
                                    unprovidedItem.getItemName(), unprovidedItem.getAmount()));
                        }
                        Helper.print("Would you like to add this branch with the unprovided items to the transport? " +
                                "(yes/no): ");
                        String proceedInput = Helper.getUserInputString();
                        while (proceedInput == null
                                || (!proceedInput.equalsIgnoreCase("yes")
                                && !proceedInput.equalsIgnoreCase("no"))) {
                            Helper.print("Invalid input. Please enter 'yes' or 'no': ");
                            proceedInput = Helper.getUserInputString();
                        }
                        if (proceedInput.equalsIgnoreCase("yes")) {
                            Helper.println("Transport routes created successfully for branch " + branchId);
                            createdTransportRoutesIds.addAll(routesCreationResult.getCreatedTransportRoutesIds());
                        } else {
                            Helper.println("Branch will not be added to the transport.");
                            List<Integer> routesIds = routesCreationResult.getCreatedTransportRoutesIds();
                            if (routesIds != null && !routesIds.isEmpty()) {
                                if (!TransportManager.deleteTransportRoutes(routesIds)) {
                                    Helper.println("Failed to delete created routes.");
                                } else {
                                    Helper.println("Created routes deleted successfully.");
                                }
                            }
                        }
                    }
                    case UNDEFINED -> {
                        Helper.println("Failed to create transport routes for branch " + branchId);
                        List<Integer> routesIds = routesCreationResult.getCreatedTransportRoutesIds();
                        if (routesIds != null && !routesIds.isEmpty()) {
                            TransportManager.deleteTransportById(routesIds.getFirst());
                            Helper.println("Deleting created routes...");
                        }
                    }
                }
            }
        } while (userInput == null || !userInput.equalsIgnoreCase("continue"));

        if (performActualTransportCreation(dateTime, createdTransportRoutesIds)) {
            Helper.println("Transport created successfully.");
        } else {
            Helper.println("Failed to create transport.");
        }
    }


    /**
     * This method is used to delete transport by its ID.
     */
    private static void deleteTransportById() {
        Helper.print("Please enter the transport ID to delete: ");
        int transportId = Helper.getUserInputInteger();
        while (transportId == Helper.UndefinedUserInput) {
            Helper.print("Invalid transport ID. Please try again: ");
            transportId = Helper.getUserInputInteger();
        }
        Transport t = TransportManager.getTransportById(transportId);
        if (t == null) {
            Helper.println("Transport not found. Please try again.");
            return;
        }
        Helper.print("Are you sure you want to delete this transport?:\n" + t.toPrettyString() + "\n(yes/no): ");
        String confirmation = Helper.getUserInputString();
        while (confirmation == null || (!confirmation.equalsIgnoreCase("yes") && !confirmation.equalsIgnoreCase("no"))) {
            Helper.print("Invalid input. Please enter 'yes' or 'no': ");
            confirmation = Helper.getUserInputString();
        }
        if (confirmation.equalsIgnoreCase("no")) {
            Helper.println("Transport deletion canceled.");
            return;
        }
        boolean success = TransportManager.deleteTransportById(transportId);
        if (success) {
            Helper.println("Transport with ID " + transportId + " deleted successfully.");
        } else {
            Helper.println("Failed to delete transport with ID " + transportId + ".");
        }
    }


    /**
     * This method is used to show all transports.
     *
     * @return A list of all transports.
     */
    private static List<Transport> showAllTransports() {
        List<Transport> transports = TransportManager.getAllTransports();
        if (transports == null || transports.isEmpty()) {
            Helper.println("No transports available.");
            return null;
        }
        Helper.println("All Transports:");
        for (Transport transport : transports) {
            Helper.println(String.format("Transport ID: %d, on date: %s", transport.getId(),
                    transport.getPlannedDateTime().format(Helper.DefaultDateTimeFormatter)));
        }
        return transports;
    }



    /**
     * This method is used to show a specific detailed transport by its ID.
     */
    private static void showSpecificDetailedTransport() {
        Helper.println("Showing specific detailed transport by ID");
        List<Transport> lst = showAllTransports();
        if (lst == null || lst.isEmpty()) {
            return;
        }
        Helper.print("Please enter the transport ID: ");
        int transportId = Helper.getUserInputInteger();
        while (transportId == Helper.UndefinedUserInput || !Helper.isValidId(lst, transportId)) {
            Helper.print("Invalid transport ID. Please try again: ");
            transportId = Helper.getUserInputInteger();
        }
        Transport transport = TransportManager.getTransportById(transportId);
        if (transport == null) {
            Helper.println("Transport not found. Please try again.");
            return;
        }
        Truck truck = TrucksManager.getTruckById(transport.getTruckId());
        if (truck == null) {
            Helper.println("Truck not found. Please try again.");
            return;
        }
        Driver driver = DriversManager.getDriverById(transport.getDriverId());
        if (driver == null) {
            Helper.println("Driver not found. Please try again.");
            return;
        }

        Helper.println("\nYou have selected transport:\n" + transport.toPrettyString());
        Helper.println("Truck details:\n" + truck.toPrettyString());
        Helper.println("Driver details:\n" + driver.toPrettyString());
        Helper.println("Displaying routes:");
        for (int routeId : transport.getTransportRouteIds()) {
            Helper.println("--------------------------------");
            Helper.println(TransportManager.getDetailedTransportRouteString(routeId));
            Helper.println("--------------------------------");
        }
        Helper.println("-End of transport details-");
    }

    /**
     * This method is used to handle the transport management menu.
     */
    public static void handleTransports() {
        boolean exit = false;
        while (!exit) {
            int choice;
            Helper.println("\nManage Transports:");
            Helper.println("1. Show all transports");
            Helper.println("2. Show specific detailed transport by ID");
            // Helper.println("3. Print transport drives for driver - by Transport ID"); TODO NTH
            // Helper.println("4. Print the next drive for driver"); TODO NTH
            Helper.println("5. Delete transport by ID");
            Helper.println("6. Create transport");
            // Helper.println("7. Start transport");
            // Helper.println("8. Visit source"); TODO NTH
            // Helper.println("9. Visit destination"); TODO NTH
            // Helper.println("10. Edit Unstarted Transport"); TODO NTH
            Helper.println("11. Back to main menu");

            do {
                Helper.print("Please choose an option: ");
                choice = Helper.getUserInputInteger();
            } while (choice == Helper.UndefinedUserInput);

            switch (choice) {
                case 1:
                    showAllTransports();
                    break;
                case 2:
                    showSpecificDetailedTransport();
                    break;
                case 3:
                    // TODO NTH Print transport drives for driver - by Transport ID
                    break;
                case 4:
                    // TODO NTH  Print the next drive for driver
                    break;
                case 5:
                    deleteTransportById();
                    break;
                case 6:
                    createTransportMainRoutine();
                    break;
                case 7:
                    // TODO NTH  Start transport
                    break;
                case 8:
                    // TODO NTH  Visit source
                    break;
                case 9:
                    // TODO NTH  Visit destination
                    break;
                case 10:
                    // TODO NTH  Edit Unstarted Transport
                    break;
                case 11:
                    exit = true;
                    break;
                default:
                    Helper.println("Invalid choice. Please try again.");
            }
        }
    }
}
