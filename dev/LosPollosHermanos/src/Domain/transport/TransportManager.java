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

import Auxiliary.Helper;
import DB.DBManager;
import DB.interfaces.ITransportController;
import Models.DBModels.transport.TransportArea;
import Models.DBModels.transport.TransportRoute;
import Models.DTOs.StockItem;

import java.util.List;

public class TransportManager {
    private static final ITransportController transportController = DBManager.getInstance().getTransportController();


    /**
     * This method is used to add a new transport.
     *
     * @param branchId      - the ID of the branch - assumes ID is correct!!!
     * @param requiredItems - the list of required items
     * @return - the transport route object if created successfully, null otherwise
     */
    public static TransportRoute createNewTransportRoute(int branchId, List<StockItem> requiredItems) {
        List<TransportArea> relevantAreas = TransportAreasManager.getTransportAreasByBranchId(branchId);

        if (relevantAreas != null && !relevantAreas.isEmpty()) {
            // need to perform algo to find the suppliers that can provide the items from this area
        }

        return null;
    }

}
