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

import Auxiliary.Enums.Models.TransportStatusEnum;
import Auxiliary.Helper;
import DB.DBManager;
import DB.implementations.MockDeliveryFileController;
import DB.implementations.MockDeliveryFileItemController;
import DB.interfaces.IDeliveryFileController;
import DB.interfaces.IDeliveryFileItemController;
import DB.interfaces.ITransportController;
import DB.interfaces.ITransportRouteController;
import Domain.suppliers.SuppliersManager;
import Models.DBModels.suppliers.Supplier;
import Models.DBModels.transport.DeliveryFile;
import Models.DBModels.transport.DeliveryFileItem;
import Models.DBModels.transport.TransportArea;
import Models.DBModels.transport.TransportRoute;
import Models.DTOs.ItemsAtSupplier;
import Models.DTOs.StockItem;

import java.util.*;

public class TransportManager {
    private static final ITransportController transportController = DBManager.getInstance().getTransportController();
    private static final IDeliveryFileItemController deliveryFileItemController = DBManager.getInstance().getDeliveryFileItemController();
    private static final IDeliveryFileController deliveryFileController = DBManager.getInstance().getDeliveryFileController();
    private static final ITransportRouteController transportRouteController = DBManager.getInstance().getTransportRouteController();


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
        ArrayList<Integer> Ids = new ArrayList<>();
        Ids.add(deliveryFileId);
        ArrayList<DeliveryFileItem> items = deliveryFileItemController.getDeliveryFileItemsByIds(Ids);
        if (items != null && !items.isEmpty()) {
            for (DeliveryFileItem item : items) {
                if (!deliveryFileItemController.deleteDeliveryFileItem(item.getId())) {
                    Helper.showError("Failed to delete delivery file item with id: " + item.getId());
                    return false;
                }
            }
        }
        return deliveryFileController.deleteDeliveryFile(deliveryFileId);
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
        TransportRoute transportRoute = new TransportRoute(Helper.UndefinedId, TransportStatusEnum.PENDING, branchId, supplierId, deliveryFileId);
        return transportRouteController.addTransportRoute(transportRoute);
    }

    /**
     * This method is used to create new transport routes for the given branch and required items.
     *
     * @param branchId      - the ID of the branch
     * @param requiredItems - the list of required items
     * @return - a list of created transport route IDs
     */
    public static List<Integer> createNewTransportRoutes(int branchId, List<StockItem> requiredItems) {
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

        if (!items.isEmpty()) {
            Helper.println("Items that are not provided for: ");
            for (StockItem item : items) {
                Helper.println(item.getItemName());
            }
            //todo need to decide how to handle a case where even after a general search we have unprovided items
        }
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
        return routes;
    }

    private static boolean deleteSourceAndDestanationSite(int id) {
        if (id <= 0) {
            Helper.showError("Cant delete site with id <= 0");
            return false;
        }
        Site site = DBManager.getInstance().getSiteController().getSiteById(id);
        if (site == null) {
            Helper.showError("Cant delete site with id=" + id + ", it does not exist");
            return false;
        } else {
            for (Site site : sites) {
                if (!DBManager.getInstance().getSiteController().deleteSite(site.getId())) {
                    Helper.showError("Cant delete site with id=" + id + ", failed to delete site");
                    return false;
                }
            }
        }
        return true;

    }

    public static boolean deleteTransportById(int transportId) {
        if(transportId <= 0) {
            Helper.showError("Cant delete transport with id <= 0");
            return false;
        }
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(transportId);
        List<TransportRoute> routes = transportRouteController.getTransportRoutesByIds(ids);
        if(routes == null || routes.isEmpty()) {
            Helper.showError("Cant delete transport with id=" + transportId + ", it does not exist");
            return false;
        }
        else {
            for(TransportRoute route : routes) {
                if(route.getStatus() != TransportStatusEnum.PENDING) {
                    Helper.showError("Cant delete transport with id=" + transportId + ", it is not in pending status");
                    return false;
                }
                if(!deleteDeliveryFile(route.getDeliveryFileId())) {
                    Helper.showError("Cant delete transport with id=" + transportId + ", failed to delete delivery file");
                    return false;
                }
                if(!deleteSourceAndDestanationSite(route.getSourceSiteId())) {
                    Helper.showError("Cant delete transport with id=" + transportId + ", failed to delete source site");
                    return false;
                }
                if(!deleteSourceAndDestanationSite(route.getDestinationSiteId())) {
                    Helper.showError("Cant delete transport with id=" + transportId + ", failed to delete destination site");
                    return false;
                }
                if(!transportRouteController.deleteTransportRoute(route.getId())) {
                    Helper.showError("Cant delete transport with id=" + transportId + ", failed to delete transport route");
                    return false;
                }
            }
        }
        return true;
    }
}
