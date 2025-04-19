package Presentation.transport;

import Auxiliary.Helper;
import Domain.stock.StockManager;
import Domain.transport.*;
import Domain.suppliers.SuppliersManager;
import Models.DBModels.BaseModel;
import Models.DBModels.transport.Driver;
import Models.DBModels.transport.Truck;
import Models.DTOs.StockItem;
import Models.DTOs.transport.CreateNewTransportRoutesResult;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransportPresentation {

    /**
     * Prints the instructions for the user.
     * This method displays the available options to the user.
     */
    private static void printInstructions() {
        Helper.println("Welcome to the Transport Management System!");
        Helper.println("Please choose an option:");
        Helper.println("1. Manage Transport Areas");
        Helper.println("2. Manage Transports");
        Helper.println("3. Manage Trucks");
        Helper.println("4. Manage Drivers");
        Helper.println("5. Manage Branches");
        Helper.println("6. Exit");
    }

    /**
     * Handles user input and calls the appropriate method based on the choice.
     *
     * @param choice the user's choice
     * @return true if the user wants to continue, false if they want to exit
     */
    private static boolean handleUserInput(int choice) {
        switch (choice) {
            case 1:
                TransportAreaHandler.HandleTransportAreaMenu();
                break;
            case 2:
                TransportHandler.handleTransports();
                break;
            case 3:
                TrucksHandler.HandleTrucks();
                break;
            case 4:
                DriversHandler.handleDrivers();
                break;
            case 5:
                BranchesHandler.handleBranches();
                break;
            case 6:
                Helper.println("Exiting the system. Goodbye!");
                return false;
            default:
                Helper.println("Invalid choice. Please try again.");
                break;
        }
        return true;
    }

    /**
     * Loads temporary mock data for testing purposes.
     * This method is used to populate the system with some initial data.
     *
     * @return true if the data was loaded successfully, false otherwise
     */
    private static boolean handleTempMockDataRuntimeLoad() {
        boolean res1 = BranchesManager.addBranch("Branch1", "Address1", "+972 5412311") &&
                BranchesManager.addBranch("Branch2", "Address2", "+972 5488899") &&
                BranchesManager.addBranch("Branch3", "Address3", "+972 5412988") &&

                DriversManager.addDriver("John", "Doe", "123456789", "+972 5412311", "C") &&
                DriversManager.addDriver("Sam", "Wilson", "987654321", "+972 5488899", "B") &&
                DriversManager.addDriver("Jane", "Smith", "456789123", "+972 5412988", "C1") &&

                TrucksManager.addTruck("5498875", 3500, 5000, "C1", "Toyota") &&
                TrucksManager.addTruck("5491176", 4000, 6000, "C", "Ford") &&
                TrucksManager.addTruck("5491177", 4500, 7000, "C1", "Mercedes") &&

                SuppliersManager.addSupplier("Supplier1", "Address1", "+972 77788855") &&
                SuppliersManager.addSupplier("Supplier2", "Address2", "+972 77788856") &&
                SuppliersManager.addSupplier("Supplier3", "Address3", "+972 77788857") &&

                TransportAreasManager.addNewTransportArea("Area1",
                        BranchesManager.getExistingBranches().subList(0, BranchesManager.getExistingBranches().size()).stream().map(BaseModel::getId).toList(),
                        SuppliersManager.getSuppliers().subList(0, BranchesManager.getExistingBranches().size()).stream().map(BaseModel::getId).toList()) &

                        TransportAreasManager.addNewTransportArea("Area2",
                                BranchesManager.getExistingBranches().subList(0, BranchesManager.getExistingBranches().size() - 1).stream().map(BaseModel::getId).toList(),
                                SuppliersManager.getSuppliers().subList(0, BranchesManager.getExistingBranches().size() - 1).stream().map(BaseModel::getId).toList());

        List<StockItem> selectedItems = StockManager.mockSearchAndReturnRelevantStockItems("", 1);
        LocalDateTime plannedDateTime = LocalDateTime.now().plusDays(1);
        CreateNewTransportRoutesResult routesCreationResult =
                TransportManager.createNewTransportRoutes(1, selectedItems);
        List<Integer> createdTransportRoutesIds = new ArrayList<>(routesCreationResult.getCreatedTransportRoutesIds());
        List<Truck> trucks = TransportManager.getAvailableTrucksByDate(plannedDateTime);
        Truck selectedTruck = null;
        Driver selectedDriver = null;
        for (Truck truck : trucks) {
            List<Driver> drivers = TransportManager.getAvailableDriversByDateAndLicense(plannedDateTime, truck.getRequiredLicenseType());
            if (drivers != null && !drivers.isEmpty()) {
                selectedTruck = truck;
                selectedDriver = drivers.getFirst();
                break;
            } else {
                Helper.showError("No available drivers found for truck with license type: " + truck.getRequiredLicenseType());
                return false;
            }
        }
        if (selectedTruck == null || selectedDriver == null) {
            Helper.showError("No available trucks or drivers found.");
            return false;
        }
        int createdTransportId = TransportManager.createTransport(plannedDateTime,
                selectedTruck.getId(), selectedDriver.getId(), createdTransportRoutesIds);

        return res1 && createdTransportId > 0;
    }

    /**
     * Main method to handle the transport presentation layer.
     * This method runs in a loop until the user chooses to exit.
     */
    public static void TransportPresentationHandler() {
        Helper.println("Loading mock data...");
        if (!handleTempMockDataRuntimeLoad()) {
            Helper.println("Failed to load mock data. Exiting.");
            return;
        }
        boolean exit = false;
        while (!exit) {
            printInstructions();
            Helper.print("Enter your choice: ");
            try {
                int choice = Helper.getUserInputInteger();
                exit = !handleUserInput(choice);
            } catch (Exception e) {
                Helper.println("An error occurred: " + e.getMessage());
            }
        }
    }
}
