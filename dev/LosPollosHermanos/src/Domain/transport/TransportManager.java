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
import Auxiliary.Helper;
import DB.DBManager;
import DB.interfaces.*;
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
                int addedId = deliveryFileItemController.addDeliveryFileItem(new DeliveryFileItem(Helper.UndefinedId, item.getItemId(), item.getAmount(), 0));
                if (addedId <= Helper.UndefinedId) {
                    Helper.showError("Failed to add item: " + item.getItemName());
                } else {
                    addedIds.add(addedId);
                }
            }
        }
        if (addedIds.isEmpty()) {
            Helper.showError("No items were added to the delivery file.");
            return Helper.UndefinedId;
        }
        return deliveryFileController.addDeliveryFile(new DeliveryFile(Helper.UndefinedId, addedIds));
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
            return Helper.UndefinedId;
        }
        Site dest = new Site(Helper.UndefinedId, SiteTypeEnum.BRANCH, branchId, b.getAddress(),
                BranchesManager.getCurrentContactPhoneOfBranch(branchId),
                BranchesManager.getCurrentContactNameOfBranch(branchId), 0);
        int destSiteId = siteController.addSite(dest);
        if (destSiteId <= Helper.UndefinedId) {
            Helper.showError("Failed to add destination site.");
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
                        items.stream().filter(item -> item.getItemId() == itemAS.getItemId()).findFirst().ifPresent(foundItem -> {
                            providedItems.add(new StockItem(itemAS.getItemId(), foundItem.getItemName(), foundItem.getBranchId(), itemAS.getAmount()));
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
            //todo remove all the created transport routes
            for (int routeId : transportRoutes) {
                TransportRoute route = transportRouteController.getTransportRouteById(routeId);
                if (route != null) {
                    //todo delete delivery file items and delivery file
                    //todo delete src and dest sites of route
                    // todo finally delete the transport route
                }
            }
        }
        else{
            for(int routeId : transportRoutes){
                TransportRoute route = transportRouteController.getTransportRouteById(routeId);
                if (route != null) {
                    Site srcSite = siteController.getSiteById(route.getSourceSiteId());
                    DeliveryFile deliveryFile = deliveryFileController.getDeliveryFileById(route.getDeliveryFileId());
                    if(srcSite != null){
                        if(srcSite.getSiteType() == SiteTypeEnum.SUPPLIER){
                            List<DeliveryFileItem> deliveryFileItems =
                                    deliveryFileItemController.getDeliveryFileItemsByIds(
                                            deliveryFile.getDeliveryFileItemIds());
                            List<ItemsAtSupplier> itemsAtSupplier =
                                    SuppliersManager.mockGetItemsAtSupplierByBarcode(srcSite.getSiteObjectId());
                            SuppliersManager.mockReserveItemsOfSupplier(srcSite.getSiteObjectId(),
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

}
