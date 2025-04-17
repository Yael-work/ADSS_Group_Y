package Domain.transport;

import Auxiliary.Enums.Domain.UpdateTransportAreaEnum;
import Auxiliary.Helper;
import DB.DBManager;
import DB.interfaces.ITransportAreaController;
import Models.DBModels.transport.TransportArea;

import java.util.ArrayList;
import java.util.List;

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
    public static boolean addNewTransportArea(String areaName, List<Integer> branchesIDs,
                                              List<Integer> suppliersIDs) {
        TransportArea area = transportAreaController.getTransportAreaByAreaName(areaName);
        if (area != null) {
            Helper.showError(String.format("Transport area with this name={%s} already exists", areaName));
            return false;
        }
        if (branchesIDs == null || branchesIDs.isEmpty()) {
            Helper.showError("Cant add transport area with empty branches IDs");
            return false;
        }
        if (suppliersIDs == null || suppliersIDs.isEmpty()) {
            Helper.showError("Cant add transport area with empty suppliers IDs");
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


    /**
     * This method is used to update a transport area.
     *
     * @param updateType   - the type of update to perform
     * @param id           - the ID of the transport area to update
     * @param AreaName     - must not be null or empty if updateType is Update_Name
     * @param suppliersIDs - must not be null or empty if updateType is Add_Suppliers or Remove_Suppliers
     * @param branchesIDs  - must not be null or empty if updateType is Add_Branches or Remove_Branches
     * @return - true if the transport area was updated successfully, false otherwise
     */
    public static boolean updateTransportArea(UpdateTransportAreaEnum updateType, int id, String AreaName,
                                              List<Integer> suppliersIDs, List<Integer> branchesIDs) {
        if (id <= 0) {
            Helper.showError("Cant update transport area with id <= 0");
            return false;
        }
        if ((AreaName == null || AreaName.isEmpty()) && updateType == UpdateTransportAreaEnum.Update_Name) {
            Helper.showError("Cant update transport area with empty name when updating name");
            return false;
        } else if (AreaName != null && !AreaName.isEmpty() && updateType == UpdateTransportAreaEnum.Update_Name) {
            // Check if the area name already exists for some other area
            TransportArea existingArea = transportAreaController.getTransportAreaByAreaName(AreaName);
            if (existingArea != null && existingArea.getId() != id) {
                Helper.showError(String.format("Cant update transport area with id=%d," +
                        " area name=%s already exists", id, AreaName));
                return false;
            }
        }
        if ((suppliersIDs == null || suppliersIDs.isEmpty()) && (updateType == UpdateTransportAreaEnum.Add_Suppliers
                || updateType == UpdateTransportAreaEnum.Remove_Suppliers)) {
            Helper.showError("Cant update transport area with empty suppliers IDs when updating suppliers");
            return false;
        }
        if ((branchesIDs == null || branchesIDs.isEmpty()) && (updateType == UpdateTransportAreaEnum.Add_Branches
                || updateType == UpdateTransportAreaEnum.Remove_Branches)) {
            Helper.showError("Cant update transport area with empty branches IDs when updating branches");
            return false;
        }
        TransportArea area = transportAreaController.getTransportAreaById(id);
        if (area == null) {
            Helper.showError(String.format("Cant update transport area with id=%d, it does not exist", id));
            return false;
        }
        switch (updateType) {
            case Update_Name -> area.setAreaName(AreaName);
            case Add_Suppliers -> {
                ArrayList<Integer> addLst = new ArrayList<>(area.getSuppliersIds());
                for (int supplierID : suppliersIDs) {
                    if (!area.getSuppliersIds().contains(supplierID)) {
                        addLst.add(supplierID);
                    }
                }
                area.setSuppliersIds(addLst);
            }
            case Remove_Suppliers -> {
                ArrayList<Integer> removeLst = new ArrayList<>(area.getSuppliersIds());
                for (int supplierID : suppliersIDs) {
                    if (area.getSuppliersIds().contains(supplierID)) {
                        removeLst.remove(supplierID);
                    }
                }
                area.setSuppliersIds(removeLst);
            }
            case Add_Branches -> {
                ArrayList<Integer> addLst = new ArrayList<>(area.getBranchesIds());
                for (int branchID : branchesIDs) {
                    if (!area.getBranchesIds().contains(branchID)) {
                        addLst.add(branchID);
                    }
                }
                area.setBranchesIds(addLst);
            }
            case Remove_Branches -> {
                ArrayList<Integer> removeLst = new ArrayList<>(area.getBranchesIds());
                for (int branchID : branchesIDs) {
                    if (area.getBranchesIds().contains(branchID)) {
                        removeLst.remove((Integer) branchID);
                    }
                }
                if (removeLst.isEmpty()) {
                    Helper.showError("Cant remove all branches from transport area, there must be at least one branch.");
                    return false;
                }
                area.setBranchesIds(removeLst);
            }
            default -> {
                Helper.showError("Cant update transport area with undefined update type");
                return false;
            }
        }
        return transportAreaController.updateTransportArea(area, id);
    }
}
