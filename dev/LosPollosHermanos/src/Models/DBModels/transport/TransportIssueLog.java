package Models.DBModels.transport;

import Models.DBModels.BaseModel;

public class TransportIssueLog extends BaseModel {
    private int currentTruckId;
    private int newTruckId;
    private int unloadedAtSiteId;
    private int unloadedItemsDeliveryFileId;

    public TransportIssueLog(int id, int currentTruckId, int newTruckId, int unloadedAtSiteId, int unloadedItemsDeliveryFileId) {
        super(id, TransportIssueLog.class.getName());
        this.currentTruckId = currentTruckId;
        this.newTruckId = newTruckId;
        this.unloadedAtSiteId = unloadedAtSiteId;
        this.unloadedItemsDeliveryFileId = unloadedItemsDeliveryFileId;
    }

    public int getCurrentTruckId() {
        return currentTruckId;
    }

    public void setCurrentTruckId(int currentTruckId) {
        this.currentTruckId = currentTruckId;
    }

    public int getNewTruckId() {
        return newTruckId;
    }

    public void setNewTruckId(int newTruckId) {
        this.newTruckId = newTruckId;
    }

    public int getUnloadedAtSiteId() {
        return unloadedAtSiteId;
    }

    public void setUnloadedAtSiteId(int unloadedAtSiteId) {
        this.unloadedAtSiteId = unloadedAtSiteId;
    }

    public int getUnloadedItemsDeliveryFileId() {
        return unloadedItemsDeliveryFileId;
    }

    public void setUnloadedItemsDeliveryFileId(int unloadedItemsDeliveryFileId) {
        this.unloadedItemsDeliveryFileId = unloadedItemsDeliveryFileId;
    }

    @Override
    public String toString() {
        return "TransportIssueLog{" +
                "currentTruckId=" + currentTruckId +
                ", newTruckId=" + newTruckId +
                ", unloadedAtSiteId=" + unloadedAtSiteId +
                ", unloadedItemsDeliveryFileId=" + unloadedItemsDeliveryFileId +
                "} " + super.toString();
    }
}
