package Presentation.transport;

import Auxiliary.Enums.Models.TransportStatusEnum;
import Auxiliary.Enums.Models.visitSourceEnum;
import Auxiliary.Helper;
import Domain.stock.StockManager;
import Domain.transport.*;
import Models.DBModels.transport.*;
import Models.DTOs.StockItem;
import Models.DTOs.transport.CreateNewTransportRoutesResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        List<DeliveryFileItem> deliveryFileItems = new ArrayList<>();
        for (int routeId : createdTransportRoutesIds) {
            TransportRoute route = TransportManager.getTransportRouteById(routeId);
            if (route != null) {
                DeliveryFile deliveryFile = TransportManager.getDeliveryFileById(route.getDeliveryFileId());
                if (deliveryFile != null) {
                    for (DeliveryFileItem item : deliveryFile.getDeliveryFileItems()) {
                        if (item != null) {
                            deliveryFileItems.add(item);
                        }
                    }
                }
            }
        }
        double sumOfItemsWeight = 0;
        for (DeliveryFileItem deliveryFileItem : deliveryFileItems) {
            StockItem stock = StockManager.getStockItemById(deliveryFileItem.getStockItemId());
            if (stock == null) {
                Helper.println("Stock item not found. Please try again.");
                return false;
            }
            if (stock.getWeight() > 0) {
                sumOfItemsWeight += stock.getWeight() * deliveryFileItem.getAmount(); //TODO check if this is correct
            } else {
                Helper.println("Invalid weight for stock item. Please try again.");
                return false;
            }
        }
        if (sumOfItemsWeight == 0) {
            Helper.println("No items selected for transport. Cannot proceed with transport creation.");
            return false;
        }
        for (Truck truck : trucks) {
            if (truck.getMaxWeight() - truck.getNetWeight() < sumOfItemsWeight) {
                trucks.remove(truck);
            }
        }
        if (trucks.isEmpty()) {
            Helper.println("No trucks available for the selected date and time.");
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
     * This method is used to start the transport.
     */
    private static void startTransport() {
        Helper.print("Please enter the transport ID to start: ");
        int transportId = Helper.getUserInputInteger();
        while (transportId == Helper.UndefinedUserInput) {
            Helper.print("Invalid transport ID. Please try again: ");
            transportId = Helper.getUserInputInteger();
        }
        Transport transport = TransportManager.getTransportById(transportId);
        if (transport == null) {
            Helper.println("Transport not found. Please try again.");
            return;
        }
        Helper.print("Enter the start date and time of the transport (%s):"
                .formatted(Helper.DefaultDateTimeFormat));
        String dateTimeStr = Helper.getUserInputString();
        LocalDateTime startDateTime = Helper.stringToDateTime(dateTimeStr);
        while (startDateTime == null) {
            Helper.print("Invalid date and time format. Please try again: ");
            dateTimeStr = Helper.getUserInputString();
            startDateTime = Helper.stringToDateTime(dateTimeStr);
        }
        if (!TransportManager.StartTransport(transport, startDateTime)) {
            Helper.println("Failed to start transport.");
            return;
        }
    }

    /**
     * This method is used to visit the source of the transport.
     */
    private static void visitSource() {
        Helper.print("Please enter the transport ID to visit source: ");
        int transportId = Helper.getUserInputInteger();
        while (transportId == Helper.UndefinedUserInput) {
            Helper.print("Invalid transport ID. Please try again: ");
            transportId = Helper.getUserInputInteger();
        }
        Transport transport = TransportManager.getTransportById(transportId);
        if (transport == null) {
            Helper.println("Transport not found. Please try again.");
            return;
        }
        if (transport.getStatus() == TransportStatusEnum.STARTED) {
            Helper.println("Relevant sources to visit: ");
            List<Integer> routes = transport.getTransportRouteIds();
            Site sourceSiteSelected = null;
            TransportRoute selectedRoute = null;
            for (Integer route : routes) {
                Site soruceSite = TransportManager.getSiteById(route);
                if (soruceSite == null) {
                    Helper.println("Source not found. Please try again.");
                    return;
                }
                Helper.println("Source ID: " + soruceSite.getSiteObjectId() + ", Address: " + soruceSite.getSiteAddress());
            }
            Helper.println("Enter the ID of the source you want to visit: ");
            do {
                int sourceID = Helper.getUserInputInteger();
                if (sourceID <= Helper.UndefinedId) {
                    Helper.println("Invalid input. Please try again.");
                    continue;
                }
                for (Integer route : routes) {
                    Site source = TransportManager.getSiteById(Objects.requireNonNull(TransportManager.getTransportRouteById(route)).getSourceSiteId());
                    if (source != null && source.getSiteObjectId() == sourceID) {
                        sourceSiteSelected = source;
                        selectedRoute = TransportManager.getTransportRouteById(route);
                        if (selectedRoute == null) {
                            Helper.println("Route not found. Please try again.");
                            return;
                        }
                        Helper.println("Visiting source: " + source.toPrettyString());
                        break;
                    }
                }
                Helper.println("Source not found. Please try again.");
            } while (sourceSiteSelected == null);
            if (TransportManager.visitSource(transport, sourceSiteSelected, selectedRoute) == visitSourceEnum.OVERLOAD) {
                do {
                    Helper.println("Overload detected. Cannot visit source. The total weight exceeds the truck's capacity.");
                    Helper.println("Choose a different truck (1) or choose which items to discard (2).");
                    String answer = Helper.getUserInputString();
                    do {
                        if (answer == null || answer.isEmpty() || !answer.matches("[1-2]")) {
                            Helper.println("Invalid input. Please choose 1 or 2.");
                        }
                        answer = Helper.getUserInputString();
                    } while (!answer.matches("[1-2]"));
                    if (answer.equals("1")) {
                        List<Truck> availableTrucks = TransportManager.getAvailableTrucksByDate(transport.getPlannedDateTime());
                        if (availableTrucks.isEmpty()) {
                            Helper.println("No available trucks found.");
                            return;
                        }
                        Helper.println("Available trucks:");
                        for (Truck availableTruck : availableTrucks) {
                            Helper.println("Truck ID: " + availableTruck.getId() + ", total Weight: " + (availableTruck.getMaxWeight() - availableTruck.getNetWeight()));
                        }
                        Helper.print("Please enter the truck ID: ");
                        int truckId = Helper.getUserInputInteger();
                        while (truckId == Helper.UndefinedUserInput) {
                            Helper.print("Invalid truck ID. Please try again: ");
                            truckId = Helper.getUserInputInteger();
                        }
                        for (Truck availableTruck : availableTrucks) {
                            if (availableTruck.getId() == truckId) {
                                transport.setTruckId(truckId);
                                Helper.println("Truck changed successfully.");
                                break;
                            }
                        }
                        if (TransportManager.visitSource(transport, sourceSiteSelected, selectedRoute) == visitSourceEnum.SUCCESS) {
                            Helper.println("Successfully visited source.");
                            break;
                        } else {
                            Helper.println("Failed to visit source. Try again.");
                        }
                    } else if (answer.equals("2")) {
                        Helper.println("Choose which items to discard:");
                        DeliveryFile deliveryFile = TransportManager.getDeliveryFileById(selectedRoute.getDeliveryFileId());
                        if (deliveryFile == null) {
                            Helper.println("Delivery file not found. Please try again.");
                            return;
                        }
                        for (int itemId : deliveryFile.getDeliveryFileItemIds()) {
                            DeliveryFileItem item = TransportManager.getDeliveryFileItemById(itemId);
                            if (item != null) {
                                Helper.println("Item ID: " + item.getStockItemId() + ", Amount: " + item.getAmount());
                            }
                        }
                        for (int itemId : deliveryFile.getDeliveryFileItemIds()) {
                            Helper.print("You want to discard this item?" + itemId + " (yes/no): ");
                            String discardItem = Helper.getUserInputString();
                            while (discardItem == null || (!discardItem.equalsIgnoreCase("yes") && !discardItem.equalsIgnoreCase("no"))) {
                                Helper.print("Invalid input. Please enter 'yes' or 'no': ");
                                discardItem = Helper.getUserInputString();
                            }
                            if (discardItem.equalsIgnoreCase("yes")) {
                                deliveryFile.getDeliveryFileItemIds().remove(itemId);
                                Helper.println("Item discarded successfully.");
                                Helper.println("You want to discard another item? (yes/no): ");
                                String discardAnotherItem = Helper.getUserInputString();
                                while (discardAnotherItem == null || (!discardAnotherItem.equalsIgnoreCase("yes") && !discardAnotherItem.equalsIgnoreCase("no"))) {
                                    Helper.print("Invalid input. Please enter 'yes' or 'no': ");
                                    discardAnotherItem = Helper.getUserInputString();
                                }
                                if (discardAnotherItem.equalsIgnoreCase("no")) {
                                    break;
                                }
                            }

                        }
                        if (TransportManager.visitSource(transport, sourceSiteSelected, selectedRoute) == visitSourceEnum.SUCCESS) {
                            Helper.println("Successfully visited source.");
                            break;
                        } else {
                            Helper.println("Failed to visit source. Try again.");
                        }
                    }
                } while (true);
            }
        }
        Helper.println("Failed to visit source. Transport not started or source not found.");
        return;
    }

    private static void visitDestination() {
        Helper.print("Please enter the transport ID to visit source: ");
        int transportId = Helper.getUserInputInteger();
        while (transportId == Helper.UndefinedUserInput) {
            Helper.print("Invalid transport ID. Please try again: ");
            transportId = Helper.getUserInputInteger();
        }
        Transport transport = TransportManager.getTransportById(transportId);
        if (transport == null) {
            Helper.println("Transport not found. Please try again.");
            return;
        }
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
            Helper.println("7. Start transport");
            Helper.println("8. Visit source");
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
                    startTransport();
                    break;
                case 8:
                    visitSource();
                    break;
                case 9:
                    visitDestination();
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
