package Domain.transport;


/*
 * 1. Show all transports -> by id and date
 * 2. Show specific detailed transport by ID -> all the static details (status, date...)
 *   + print all linked transport routes -> also print the delivery file of each route
 *   + print all linked issue logs -> also print the issue file of each log
 * 3. Print transport drives for driver - by Transport ID:
 *   A. Take all the linked transport routes of the transport ID
 *   B. Make a dict (or pair?) of all the routes by their branch ID {"branch_id":[<transport_route_id>,...]...}
 *   C. For each branch ID, check if they started any transport route and haven't finished all.(create list of boolean as the len of the branches and each of them true or false).
 *        In case that none started, pick random branch id.
 *   C. Using the picked branch id - aggregate the relevant Transport Routes and return.
 * [???]. Maybe also do something like [3] but with the option to see all pending routes according to branch id??
 * 4. Print the next drive for driver:
 *    same like in [3] but only take the first route of the aggregated routes.
 * 5. Delete transport by ID -> delete all linked inner data.
 * 6. Create transport:
 *   A. Ask for planned date time of transport
 *   B. Ask to add branch(es) to the transport:
 *       for each branch:
 *       [a]. Search for item by name or barcode [happens in presentation - we will provide the items to the manager]
 *       [b]. search for all transport areas that contains this branch
 *       [c]. extract all the suppliers that are linked to the areas -> remove duplicates from the list!
 *       [d]. make a list of items that each supplier can provide - see if there are any items that are UNPROVIDED for
 *           [[*]]. if there are any items that are UNPROVIDED for, look for other suppliers that can provide them
 *               from general supplier list (exclude all the suppliers that we listed in step [c]).
 *       [e]. in case there are still items that are UNPROVIDED for, we will  alert the user and ask if he wants
 *           to perform the transport anyway.
 *       [f]. if the user agrees, create a transport route between each supplier and the branch with the relevant items.
 *   C. Ask to pick a suitable truck.
 *   D. Ask to pick a driver - note - the driver must have the proper license!
 * 7. Start transport:TODO
 * 8. Visit source:TODO
 * 9. Visit destination:TODO
 * 10. EditUnstartedTransport:TODO
 * */

import Auxiliary.Enums.Domain.CreateNewTransportRouteResultEnum;
import Auxiliary.Enums.Models.SiteTypeEnum;
import Auxiliary.Enums.Models.TransportStatusEnum;
import Auxiliary.Enums.Models.visitSiteEnum;
import Auxiliary.Helper;
import DB.DBManager;
import DB.interfaces.*;
import DB.interfaces.IDeliveryFileController;
import DB.interfaces.IDeliveryFileItemController;
import DB.interfaces.ITransportController;
import DB.interfaces.ITransportRouteController;
import Domain.stock.StockManager;
import Domain.suppliers.SuppliersManager;
import Models.DBModels.suppliers.Supplier;
import Models.DBModels.transport.*;
import Models.DTOs.ItemsAtSupplier;
import Models.DTOs.StockItem;
import Models.DTOs.transport.CreateNewTransportRoutesResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TransportManager {
    private static final ITransportController transportController = DBManager.getInstance().getTransportController();
    private static final IDeliveryFileItemController deliveryFileItemController = DBManager.getInstance().getDeliveryFileItemController();
    private static final IDeliveryFileController deliveryFileController = DBManager.getInstance().getDeliveryFileController();
    private static final ITransportRouteController transportRouteController = DBManager.getInstance().getTransportRouteController();
    private static final ISiteController siteController = DBManager.getInstance().getSiteController();

    /**
     * This method is used to create a delivery file.
     *
     * @param requiredItems - the list of required items
     * @return - the ID of the created delivery file, or Helper.UndefinedId if failed
     */
    private static int createDeliveryFile(List<StockItem> requiredItems) {
        if (requiredItems == null || requiredItems.isEmpty()) {
            Helper.showError("No items to create a delivery file.");
            return Helper.UndefinedId;
        }
        List<Integer> addedIds = new ArrayList<>();
        for (StockItem item : requiredItems) {
            if (item.getAmount() <= 0) {
                Helper.showError("Invalid amount for item: " + item.getItemName());
            } else {
                int addedId = deliveryFileItemController.addDeliveryFileItem(new DeliveryFileItem(Helper.UndefinedId, item.getStockItemId(), item.getAmount(), 0));
                if (addedId <= Helper.UndefinedId) {
                    Helper.showError("Failed to add item: " + item.getItemName());
                } else {
                    addedIds.add(addedId);
                }
            }
        }
        List<DeliveryFileItem> addedItems = deliveryFileItemController.getDeliveryFileItemsByIds(addedIds);
        if (addedIds.isEmpty() || addedItems == null || addedItems.isEmpty()) {
            Helper.showError("No items were added to the delivery file.");
            return Helper.UndefinedId;
        }
        return deliveryFileController.addDeliveryFile(new DeliveryFile(Helper.UndefinedId, addedItems));
    }

    /**
     * This method is used to delete a delivery file and its items.
     *
     * @param deliveryFileId - the ID of the delivery file to be deleted
     * @return - true if the delivery file was deleted successfully, false otherwise
     */
    private static boolean deleteDeliveryFile(int deliveryFileId) {
        if (deliveryFileId <= 0) {
            Helper.showError("Cant delete delivery file with id <= 0");
            return false;
        }
        DeliveryFile deliveryFile = deliveryFileController.getDeliveryFileById(deliveryFileId);
        if (deliveryFile == null) {
            Helper.showError("Cant delete delivery file with id=" + deliveryFileId + ", it does not exist");
            return false;
        }
        List<DeliveryFileItem> items = deliveryFile.getDeliveryFileItems();
        if (items != null && !items.isEmpty()) {
            for (DeliveryFileItem item : items) {
                if (!deliveryFileItemController.deleteDeliveryFileItem(item.getId())) {
                    Helper.showError("Failed to delete delivery file item with id: " + item.getId());
                    return false;
                }
            }
            if (!deliveryFileController.deleteDeliveryFile(deliveryFileId)) {
                Helper.showError("Failed to delete delivery file with id: " + deliveryFileId);
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * This method is used to create a transport route.
     *
     * @param branchId   - the ID of the branch - assumes ID is correct!!!
     * @param supplierId - the ID of the supplier - assumes ID is correct!!!
     * @param items      - the list of items to be delivered
     * @return - the ID of the created transport route, or Helper.UndefinedId if failed
     */
    private static int createTransportRoute(int branchId, int supplierId, List<StockItem> items) {
        if (items == null || items.isEmpty()) {
            Helper.showError("No items to create a transport route.");
            return Helper.UndefinedId;
        }
        int deliveryFileId = createDeliveryFile(items);
        if (deliveryFileId <= Helper.UndefinedId) {
            Helper.showError("Failed to create delivery file for transport route.");
            return Helper.UndefinedId;
        }
        Branch b = BranchesManager.getBranchById(branchId);
        if (b == null) {
            Helper.showError("Invalid branch ID: " + branchId);
            return Helper.UndefinedId;
        }
        Supplier s = SuppliersManager.getSupplierById(supplierId);
        if (s == null) {
            Helper.showError("Invalid supplier ID: " + supplierId);
            return Helper.UndefinedId;
        }
        Site src = new Site(Helper.UndefinedId, SiteTypeEnum.SUPPLIER, supplierId, s.getSupplierAddress(),
                SuppliersManager.getCurrentContactPhoneOfSupplier(supplierId),
                SuppliersManager.getCurrentContactNameOfSupplier(supplierId), 0);
        int srcSiteId = siteController.addSite(src);
        if (srcSiteId <= Helper.UndefinedId) {
            Helper.showError("Failed to add source site.");
            if (!deleteDeliveryFile(deliveryFileId)) {
                Helper.showError("Failed to delete delivery file");
            }
            return Helper.UndefinedId;
        }
        Site dest = new Site(Helper.UndefinedId, SiteTypeEnum.BRANCH, branchId, b.getAddress(),
                BranchesManager.getCurrentContactPhoneOfBranch(branchId),
                BranchesManager.getCurrentContactNameOfBranch(branchId), 0);
        int destSiteId = siteController.addSite(dest);
        if (destSiteId <= Helper.UndefinedId) {
            Helper.showError("Failed to add destination site.");
            if (!deleteDeliveryFile(deliveryFileId)) {
                Helper.showError("Failed to delete delivery file");
            }
            if (!deleteSite(srcSiteId)) {
                Helper.showError("Failed to delete source site");
            }
            return Helper.UndefinedId;
        }

        TransportRoute transportRoute = new TransportRoute(Helper.UndefinedId, TransportStatusEnum.PENDING,
                deliveryFileId, srcSiteId, destSiteId);
        return transportRouteController.addTransportRoute(transportRoute);
    }

    /**
     * This method is used to create new transport routes for the given branch and required items.
     *
     * @param branchId      - the ID of the branch
     * @param requiredItems - the list of required items
     * @return - a list of created transport route IDs
     */
    public static CreateNewTransportRoutesResult createNewTransportRoutes(int branchId, List<StockItem> requiredItems) {
        List<Integer> routes = new ArrayList<>();
        List<StockItem> items = new ArrayList<>(requiredItems);
        Map<Integer, List<StockItem>> suppliersAndItems = new HashMap<>();
        List<TransportArea> transportAreas = DBManager.getInstance().getTransportAreaController().getAllTransportAreasByBranchId(branchId);
        if (transportAreas != null && !transportAreas.isEmpty()) {
            Set<Supplier> suppliers = new HashSet<>();
            for (TransportArea area : transportAreas) {
                suppliers.addAll(SuppliersManager.getSuppliersByIds(area.getSuppliersIds()));
            }
            if (!suppliers.isEmpty()) {
                for (Supplier s : suppliers) {
                    List<ItemsAtSupplier> itemsAtSupplier = SuppliersManager.mockCanSupplierProvideRequestedItems(s.getId(), items);
                    if (itemsAtSupplier.isEmpty()) {
                        // No items can be provided by this supplier
                        continue;
                    }
                    List<StockItem> providedItems = new ArrayList<>();
                    for (ItemsAtSupplier itemAS : itemsAtSupplier) {
                        items.stream().filter(item -> item.getStockItemId() == itemAS.getStockItemId()).findFirst().ifPresent(foundItem -> {
                            providedItems.add(foundItem);
                            items.remove(foundItem);
                        });
                    }
                    if (!suppliersAndItems.containsKey(s.getId())) {
                        suppliersAndItems.put(s.getId(), providedItems);
                    } else {
                        suppliersAndItems.get(s.getId()).addAll(providedItems);
                    }
                }
                if (items != null && !items.isEmpty()) {
                    //todo handle general search for unprovided items...
                }
            } else {
                // todo handle general search...
            }
        } else {
            // todo handle general search...
        }

        boolean someItemsUnprovided = !items.isEmpty();
        for (Map.Entry<Integer, List<StockItem>> entry : suppliersAndItems.entrySet()) {
            int supplierId = entry.getKey();
            List<StockItem> itemsAtSupplier = entry.getValue();
            int routeId = createTransportRoute(branchId, supplierId, itemsAtSupplier);
            if (routeId > Helper.UndefinedId) {
                routes.add(routeId);
            } else {
                Helper.showError("Failed to create transport route for supplier: " + supplierId);
            }
        }
        return new CreateNewTransportRoutesResult(routes,
                someItemsUnprovided ? CreateNewTransportRouteResultEnum.SomeItemsUnprovided :
                        CreateNewTransportRouteResultEnum.Success,
                items);
    }

    /**
     * This method is used to delete a site
     *
     * @param siteId - the ID of the delivery file to be deleted
     * @return - true if the delivery file was deleted successfully, false otherwise
     */
    private static boolean deleteSite(int siteId) {
        if (siteId <= 0) {
            Helper.showError("Cant delete site with id <= 0");
            return false;
        }
        Site site = DBManager.getInstance().getSiteController().getSiteById(siteId);
        if (site == null) {
            Helper.showError("Cant delete site with id=" + siteId + ", it does not exist");
            return false;
        } else {
            if (!DBManager.getInstance().getSiteController().deleteSite(siteId)) {
                Helper.showError("Cant delete site with id=" + siteId + ", failed to delete site");
                return false;
            } else {
                return true;
            }
        }
    }


    /**
     * This method is used to delete transport routes.
     *
     * @param routes - the list of transport routes IDs to be deleted
     * @return - true if the transport routes were deleted successfully, false otherwise
     */
    public static boolean deleteTransportRoutes(List<Integer> routes) {
        if (routes == null || routes.isEmpty()) {
            Helper.showError("Cant delete transport, it does not exist");
            return false;
        } else {
            for (Integer routeId : routes) {
                TransportRoute t = transportRouteController.getTransportRouteById(routeId);
                if (t == null) {
                    Helper.showError("Cant delete transport with id=" + routeId + ", it does not exist");
                    return false;
                }
                if (t.getStatus() != TransportStatusEnum.PENDING) {
                    Helper.showError("Cant delete transport with id=" + t.getId() + ", it is not in pending status");
                    return false;
                }
                if (!deleteDeliveryFile(t.getDeliveryFileId())) {
                    Helper.showError("Cant delete transport with id=" + t.getId() + ", failed to delete delivery file");
                    return false;
                }
                if (!deleteSite(t.getSourceSiteId())) {
                    Helper.showError("Cant delete transport with id=" + t.getId() + ", failed to delete source site");
                    return false;
                }
                if (!deleteSite(t.getDestinationSiteId())) {
                    Helper.showError("Cant delete transport with id=" + t.getId() + ", failed to delete destination site");
                    return false;
                }
                if (!transportRouteController.deleteTransportRoute(t.getId())) {
                    Helper.showError("Cant delete transport with id=" + t.getId() + ", failed to delete transport route");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method is used to delete transport by ID.
     *
     * @param transportId - the ID of the transport to be deleted
     * @return - true if the transport was deleted successfully, false otherwise
     */
    public static boolean deleteTransportById(int transportId) {
        if (transportId <= 0) {
            Helper.showError("Cant delete transport with id <= 0");
            return false;
        }
        Transport transport = transportController.getTransportById(transportId);
        if (transport == null) {
            Helper.showError("Cant delete transport with id=" + transportId + ", it does not exist");
            return false;
        }
        List<TransportRoute> routes = transportRouteController.getTransportRoutesByIds((ArrayList<Integer>) transport.getTransportRouteIds());
        if (routes == null || routes.isEmpty()) {
            Helper.showError("Cant delete transport with id=" + transportId + ", it does not exist");
            return false;
        }
        List<Integer> routeIds = new ArrayList<>();
        for (TransportRoute route : routes) {
            routeIds.add(route.getId());
        }
        if (!deleteTransportRoutes(routeIds)) {
            Helper.showError("Cant delete transport with id=" + transportId + ", failed to delete transport routes");
            return false;
        }
        if (!transportController.deleteTransport(transportId)) {
            Helper.showError("Cant delete transport with id=" + transportId + ", failed to delete transport");
            return false;
        }
        return true;
    }


    /**
     * Method return ONLY the available trucks for the given date.
     * Available trucks are those that are not assigned to any transport on the given date (excluding the time part).
     *
     * @param targetDateTime - the date to check for available trucks
     * @return - a list of available trucks
     */
    public static List<Truck> getAvailableTrucksByDate(LocalDateTime targetDateTime) {
        List<Truck> availableTrucks = TrucksManager.getAllTrucks();
        List<Transport> transports = transportController.getAllRecords();
        // Extract the target date (without time)
        LocalDate targetDate = targetDateTime.toLocalDate();

        // Find all truck IDs that are already assigned to transport on that date
        Set<Integer> busyTruckIds = transports.stream()
                .filter(t -> {
                    TransportStatusEnum status = t.getStatus();
                    boolean isActive = status == TransportStatusEnum.PENDING || status == TransportStatusEnum.STARTED;
                    return isActive && t.getPlannedDateTime().toLocalDate().equals(targetDate);
                })
                .map(Transport::getTruckId)
                .collect(Collectors.toSet());

        // Filter out busy trucks
        return availableTrucks.stream()
                .filter(truck -> !busyTruckIds.contains(truck.getId()))
                .collect(Collectors.toList());
    }


    /**
     * Method return ONLY the available drivers for the given date.
     * Available drivers are those that are not assigned to any transport on the given date (excluding the time part).
     *
     * @param targetDateTime - the date to check for available drivers
     * @return - a list of available drivers
     */
    public static List<Driver> getAvailableDriversByDateAndLicense(LocalDateTime targetDateTime, String truckLicenseType) {
        List<Driver> availableDrivers = DriversManager.getAllDrivers();
        List<Transport> transports = transportController.getAllRecords();
        // Extract the target date (without time)
        LocalDate targetDate = targetDateTime.toLocalDate();

        // Find all driver IDs that are already assigned to transport on that date
        Set<Integer> busyDriverIds = transports.stream()
                .filter(t -> {
                    TransportStatusEnum status = t.getStatus();
                    boolean isActive = status == TransportStatusEnum.PENDING || status == TransportStatusEnum.STARTED;
                    return isActive && t.getPlannedDateTime().toLocalDate().equals(targetDate);
                })
                .map(Transport::getDriverId)
                .collect(Collectors.toSet());

        // Filter out busy drivers
        return availableDrivers.stream()
                .filter(driver -> !busyDriverIds.contains(driver.getId())
                        && Helper.isLicenseTypeValidPerTruck(driver.getLicenseType(), truckLicenseType))
                .collect(Collectors.toList());
    }


    /**
     * This method is used to create transport.
     *
     * @param plannedDateTime - the planned date and time for the transport
     * @param truckId         - the ID of the truck
     * @param driverId        - the ID of the driver
     * @param transportRoutes - the list of transport route IDs
     * @return - the ID of the created transport, or Helper.UndefinedId if failed
     */
    public static int createTransport(LocalDateTime plannedDateTime, int truckId, int driverId, List<Integer> transportRoutes) {
        if (plannedDateTime == null || truckId <= Helper.UndefinedId || driverId <= Helper.UndefinedId
                || transportRoutes == null || transportRoutes.isEmpty()) {
            Helper.showError("Invalid input for creating transport.");
            return Helper.UndefinedId;
        }
        Transport transport = new Transport(Helper.UndefinedId, plannedDateTime, TransportStatusEnum.PENDING,
                truckId, driverId, transportRoutes, null, null, null);
        int transportId = transportController.addTransport(transport);
        if (transportId <= Helper.UndefinedId) {
            Helper.showError("Failed to create transport.");
            if (deleteTransportRoutes(transportRoutes)) {
                Helper.println("Deleted successfully the transport routes");
            } else {
                Helper.showError("Failed to delete transport routes");
            }
            return Helper.UndefinedId;
        } else {
            for (int routeId : transportRoutes) {
                TransportRoute route = transportRouteController.getTransportRouteById(routeId);
                if (route != null) {
                    Site srcSite = siteController.getSiteById(route.getSourceSiteId());
                    DeliveryFile deliveryFile = deliveryFileController.getDeliveryFileById(route.getDeliveryFileId());
                    if (srcSite != null) {
                        if (srcSite.getSiteType() == SiteTypeEnum.SUPPLIER) {
                            List<DeliveryFileItem> deliveryFileItems = deliveryFile.getDeliveryFileItems();
                            List<StockItem> items = new ArrayList<>();
                            List<ItemsAtSupplier> itemsAtSupplier =
                                    SuppliersManager.mockGetSupplierItemsByStockItems(srcSite.getSiteObjectId(),
                                            StockManager.mockGetStockItemsByIds(
                                                    deliveryFileItems.stream()
                                                            .map(DeliveryFileItem::getStockItemId)
                                                            .collect(Collectors.toList())));
                            if (!SuppliersManager.mockReserveItemsOfSupplier(srcSite.getSiteObjectId(), itemsAtSupplier)) {
                                Helper.showError("Failed to reserve items for supplier: " + srcSite.getSiteObjectId());
                            }
                        }
                    }

                }
            }
        }
        return transportId;
    }


    /**
     * This method is used to get transport by ID.
     *
     * @return - the transport object, or null if not found
     */
    public static Transport getTransportById(int transportId) {
        if (transportId <= Helper.UndefinedId) {
            Helper.showError("Invalid transport ID.");
            return null;
        }
        Transport transport = transportController.getTransportById(transportId);
        if (transport == null) {
            Helper.showError("Transport not found.");
        }
        return transport;
    }

    /**
     * This method is used to get all transports.
     *
     * @return - a list of all transports
     */
    public static List<Transport> getAllTransports() {
        return transportController.getAllRecords();
    }


    /**
     * This method is used to get a detailed string representation of a delivery file.
     *
     * @param deliveryFileId - the ID of the delivery file
     * @return - a detailed string representation of the delivery file
     */
    private static String getDetailedDeliveryFileString(int deliveryFileId) {
        if (deliveryFileId <= Helper.UndefinedId) {
            Helper.showError("Cant get delivery file with id <= 0");
            return null;
        }
        DeliveryFile deliveryFile = deliveryFileController.getDeliveryFileById(deliveryFileId);
        if (deliveryFile == null) {
            Helper.showError("Cant get delivery file with id=" + deliveryFileId + ", it does not exist");
            return null;
        }
        List<DeliveryFileItem> deliveryFileItems = deliveryFile.getDeliveryFileItems();
        if (deliveryFileItems == null || deliveryFileItems.isEmpty()) {
            Helper.showError("Cant get delivery file with id=" + deliveryFileId + ", it does not exist");
            return null;
        }
        List<StockItem> items = StockManager.mockGetStockItemsByIds(deliveryFileItems.stream()
                .map(DeliveryFileItem::getStockItemId)
                .collect(Collectors.toList()));
        if (items.isEmpty()) {
            Helper.showError("Cant get delivery file with id=" + deliveryFileId + ", it does not exist");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Delivery File ID: ").append(deliveryFile.getId()).append("\n");
        sb.append("Items:");
        for (DeliveryFileItem item : deliveryFileItems) {
            items.stream().filter(i -> i.getStockItemId() == item.getStockItemId())
                    .findFirst()
                    .ifPresent(stockItem -> sb.append("\n - ")
                            .append(stockItem.getItemName())
                            .append(": Amount - ")
                            .append(item.getAmount())
                            .append(" Amount Picked Up - ")
                            .append(item.getAmountPickedUp()));
        }
        return sb.toString();
    }


    /**
     * This method is used to get a detailed string representation of a transport route.
     *
     * @param routeId - the ID of the transport route
     * @return - a detailed string representation of the transport route
     */
    public static String getDetailedTransportRouteString(int routeId) {
        if (routeId <= Helper.UndefinedId) {
            Helper.showError("Cant get transport route with id <= 0");
            return null;
        }
        TransportRoute route = transportRouteController.getTransportRouteById(routeId);
        if (route == null) {
            Helper.showError("Cant get transport route with id=" + routeId + ", it does not exist");
            return null;
        }
        String deleiveryFileDetailedString = getDetailedDeliveryFileString(route.getDeliveryFileId());

        Site sourceSite = siteController.getSiteById(route.getSourceSiteId());
        if (sourceSite == null) {
            Helper.showError("Cant get transport route with id=" + routeId + ", it does not exist");
            return null;
        }
        Site destinationSite = siteController.getSiteById(route.getDestinationSiteId());
        if (destinationSite == null) {
            Helper.showError("Cant get transport route with id=" + routeId + ", it does not exist");
            return null;
        }

        return "Transport Route ID: " + route.getId() + "\n" +
                "Status: " + route.getStatus() + "\n" +
                "Source Site: " + sourceSite.toPrettyString() + "\n" +
                "Destination Site: " + destinationSite.toPrettyString() + "\n" +
                "Delivery File: \n" +
                deleiveryFileDetailedString;
    }

    public static TransportRoute getTransportRouteById(int routeId) {
        if (routeId <= Helper.UndefinedId) {
            Helper.showError("Cant get transport route with id <= 0");
            return null;
        }
        TransportRoute route = transportRouteController.getTransportRouteById(routeId);
        if (route == null) {
            Helper.showError("Cant get transport route with id=" + routeId + ", it does not exist");
            return null;
        }
        return route;
    }

    public static DeliveryFile getDeliveryFileById(int deliveryFileId) {
        if (deliveryFileId <= Helper.UndefinedId) {
            Helper.showError("Cant get delivery file with id <= 0");
            return null;
        }
        DeliveryFile deliveryFile = deliveryFileController.getDeliveryFileById(deliveryFileId);
        if (deliveryFile == null) {
            Helper.showError("Cant get delivery file with id=" + deliveryFileId + ", it does not exist");
            return null;
        }
        return deliveryFile;
    }

    public static DeliveryFileItem getDeliveryFileItemById(int itemId) {
        if (itemId <= Helper.UndefinedId) {
            Helper.showError("Cant get delivery file item with id <= 0");
            return null;
        }
        DeliveryFileItem deliveryFileItem = deliveryFileItemController.getDeliveryFileItemById(itemId);
        if (deliveryFileItem == null) {
            Helper.showError("Cant get delivery file item with id=" + itemId + ", it does not exist");
            return null;
        }
        return deliveryFileItem;
    }

    public static boolean StartTransport(Transport transport, LocalDateTime startDateTime) {
        if (transport.getStatus() == TransportStatusEnum.PENDING) {
            LocalDateTime plannedDateTime = transport.getPlannedDateTime();
            if (startDateTime.isBefore(plannedDateTime) || startDateTime.isAfter(plannedDateTime)) {
                Helper.println("The start date and time cannot be before the planned date and time.");
                return false;
            }
            Helper.print("Are you sure you want to start the transport? (yes/no)");
            String answer = Helper.getUserInputString();
            if (answer == null || answer.isEmpty()) {
                Helper.println("Invalid input. Transport start cancelled.");
                return false;
            }
            if (!answer.equalsIgnoreCase("yes")) {
                Helper.println("Transport start cancelled.");
                return false;
            }
            transport.setStatus(TransportStatusEnum.STARTED);
            Helper.println("Transport started successfully.");
            return true;
        } else {
            Helper.println("Transport is already started or completed. Cannot start again.");
            return false;
        }
    }

    /**
     * This method is used to visit the source site of a transport route.
     *
     * @param transport     - the transport object
     * @param selectedRoute - the selected transport route
     * @return - an enum indicating the result of the visit
     */
    public static visitSiteEnum visitSource(Transport transport, TransportRoute selectedRoute) {
        DeliveryFile deliveryFile = deliveryFileController.getDeliveryFileById(selectedRoute.getDeliveryFileId());
        if (deliveryFile == null) {
            Helper.println("Delivery file not found.");
            return visitSiteEnum.FAILED;
        }
        List<DeliveryFileItem> deliveryFileItems = deliveryFile.getDeliveryFileItems();
        if (deliveryFileItems == null || deliveryFileItems.isEmpty()) {
            Helper.println("No items in the delivery file.");
            return visitSiteEnum.FAILED;
        }
        Truck truck = TrucksManager.getTruckById(transport.getTruckId());
        if (truck == null) {
            Helper.println("Truck not found.");
            return visitSiteEnum.FAILED;
        }
        double totalWeight = 0;
        for (DeliveryFileItem item : deliveryFileItems) {
            StockItem stockItem = StockManager.getStockItemById(item.getStockItemId());
            if (stockItem == null) {
                Helper.println("Stock item not found.");
                return visitSiteEnum.FAILED;
            }
            totalWeight += stockItem.getWeight() * item.getAmountPickedUp();
        }
        if (totalWeight > truck.getMaxWeight() - truck.getNetWeight()) {
            return visitSiteEnum.OVERLOAD;
        }
        if (selectedRoute.getStatus() != TransportStatusEnum.SOURCE_COMPLETED_WITH_ISSUES) {
            selectedRoute.setStatus(TransportStatusEnum.SOURCE_COMPLETED);
        }
        return visitSiteEnum.SUCCESS;
    }

    /**
     * This method is used to visit the destination site.
     *
     * @param transport     - the transport object
     * @param selectedRoute - the selected transport route
     * @return - an enum indicating the result of the visit
     */
    public static visitSiteEnum visitDestination(Transport transport, TransportRoute selectedRoute) {
        DeliveryFile deliveryFile = deliveryFileController.getDeliveryFileById(selectedRoute.getDeliveryFileId());
        if (deliveryFile == null) {
            Helper.println("Delivery file not found.");
            return visitSiteEnum.FAILED;
        }
        List<DeliveryFileItem> deliveryFileItems = deliveryFileItemController.getDeliveryFileItemsByIds(deliveryFile.getDeliveryFileItemIds());
        if (deliveryFileItems == null || deliveryFileItems.isEmpty()) {
            Helper.println("No items in the delivery file.");
            return visitSiteEnum.FAILED;
        }
        if (selectedRoute.getStatus() != TransportStatusEnum.DESTINATION_COMPLETED_WITH_ISSUES) {
            selectedRoute.setStatus(TransportStatusEnum.DESTINATION_COMPLETED);
        }
        selectedRoute.setStatus(TransportStatusEnum.DESTINATION_COMPLETED);
        return visitSiteEnum.SUCCESS;
    }

    /**
     * This method is used to get a site by ID.
     *
     * @param SiteId - the ID of the site
     * @return - the site object, or null if not found
     */
    public static Site getSiteById(int SiteId) {
        if (SiteId <= Helper.UndefinedId) {
            Helper.showError("Cant get site with id <= 0");
            return null;
        }
        Site site = siteController.getSiteById(SiteId);
        if (site == null) {
            Helper.showError("Cant get site with id=" + SiteId + ", it does not exist");
            return null;
        }
        return site;
    }

    public static boolean UpdateTransportIssue(Transport transport, int code, TransportRoute selectedRoute, int truckId, DeliveryFile deliveryFile, int codeSite) {
        if (selectedRoute == null) {
            Helper.println("Selected route is null.");
        } else {
            if (codeSite == 0) {
                if (code == 1) {
                    if (transport.getTransportIssueLogIds().isEmpty()) {
                        List<Integer> transportIssueLogIds = new ArrayList<>();
                        TransportIssueLog transportIssueLog = new TransportIssueLog(1, transport.getTruckId(), truckId,
                                selectedRoute.getId(), 0);
                        transportIssueLogIds.add(transportIssueLog.getId());
                        transport.setTransportIssueLogIds(transportIssueLogIds);
                    }
                    TransportIssueLog transportIssueLog = new TransportIssueLog(transport.getTransportIssueLogIds().getLast() + 1, transport.getTruckId(), truckId,
                            selectedRoute.getId(), 0);
                    transport.getTransportIssueLogIds().add(transportIssueLog.getId());
                }
                if (code == 2) {
                    selectedRoute.setDeliveryFileId(deliveryFile.getId());
                }
                selectedRoute.setStatus(TransportStatusEnum.SOURCE_COMPLETED_WITH_ISSUES);
                transport.setStatus(TransportStatusEnum.CONTINUED_WITH_ISSUES);
                return true;
            }
            if (codeSite == 1) {
                /* Optional for now */
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfTransportCompleted(Transport transport) {
        boolean isCompleted = true;
        for (Integer route : transport.getTransportRouteIds()) {
            TransportRoute transportRoute = transportRouteController.getTransportRouteById(route);
            if (transportRoute == null) {
                Helper.println("Transport route not found.");
                isCompleted = false;
                break;
            }
            if (transportRoute.getStatus() != TransportStatusEnum.DESTINATION_COMPLETED) {
                isCompleted = false;
                break;
            }
        }
        if (isCompleted) {
            if (transport.getStatus() == TransportStatusEnum.CONTINUED_WITH_ISSUES) {
                transport.setStatus(TransportStatusEnum.DONE_WITH_ISSUES);
            } else {
                transport.setStatus(TransportStatusEnum.DONE);
            }
            return true;
        } else {
            return false;
        }
    }
}
