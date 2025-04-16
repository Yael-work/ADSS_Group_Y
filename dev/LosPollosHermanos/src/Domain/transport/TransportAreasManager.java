package Domain.transport;

import Auxiliary.Helper;
import DB.DBManager;
import DB.interfaces.ITransportAreaController;
import Models.DBModels.transport.TransportArea;

import java.util.ArrayList;

public class TransportAreasManager {
    private static final ITransportAreaController transportAreaController = DBManager.getInstance()
            .getTransportAreaController();

    /**
     * This method is used to add a new transport area.
     *
     * @param areaName     - the name of the transport area
     * @param branchesIDs  - the IDs of the branches in the transport area - assumes IDs are correct
     * @param suppliersIDs - the IDs of the suppliers in the transport area - assumes IDs are correct
     * @return - true if the transport area was added successfully, false otherwise
     */
    public static boolean addNewTransportArea(String areaName, ArrayList<Integer> branchesIDs,
                                              ArrayList<Integer> suppliersIDs) {
        TransportArea area = transportAreaController.getTransportAreaByAreaName(areaName);
        if (area != null) {
            Helper.showError(String.format("Transport area with this name={%s} already exists", areaName));
            return false;
        }
        if (branchesIDs == null || branchesIDs.isEmpty()) {
            Helper.showError("Branches IDs list is empty");
            return false;
        }
        if (suppliersIDs == null || suppliersIDs.isEmpty()) {
            Helper.showError("Suppliers IDs list is empty");
            return false;
        }

        area = new TransportArea(Helper.UndefinedId, areaName, suppliersIDs, branchesIDs);
        return transportAreaController.addTransportArea(area);
    }

    /**
     * This method is used to get a transport area by its ID.
     *
     * @param id - the ID of the transport area
     * @return - the transport area object if found, otherwise null
     */
    public static boolean removeTransportArea(int id) {
        if (id <= 0) {
            Helper.showError("Cant delete transport area with id <= 0");
            return false;
        }
        return transportAreaController.deleteTransportArea(id);
    }

    /**
     * This method is used to get a transport area by its ID.
     *
     * @param id - the ID of the transport area
     * @return - the transport area object if found, otherwise null
     */
    public static TransportArea getTransportAreaById(int id) {
        return transportAreaController.getTransportAreaById(id);
    }

    /**
     * Get all the existing transports areas.
     *
     * @return - a list of all transport areas
     */
    public static ArrayList<TransportArea> getAllTransportAreas() {
        return transportAreaController.getAllRecords();
    }


}
