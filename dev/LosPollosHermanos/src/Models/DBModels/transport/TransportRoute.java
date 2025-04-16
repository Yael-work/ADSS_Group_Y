package Models.DBModels.transport;

import Auxiliary.Enums.Models.TransportStatusEnum;
import Models.DBModels.BaseModel;

public class TransportRoute extends BaseModel {
    private TransportStatusEnum status;
    private int deliveryFileId;
    private int sourceSiteId;
    private int destinationSiteId;

    public TransportRoute(int id, TransportStatusEnum status, int deliveryFileId, int sourceSiteId, int destinationSiteId) {
        super(id, TransportRoute.class.getName());
        this.status = status;
        this.deliveryFileId = deliveryFileId;
        this.sourceSiteId = sourceSiteId;
        this.destinationSiteId = destinationSiteId;
    }

    public int getDeliveryFileId() {
        return deliveryFileId;
    }

    public int getDestinationSiteId() {
        return destinationSiteId;
    }

    public int getSourceSiteId() {
        return sourceSiteId;
    }

    public void setDeliveryFileId(int deliveryFileId) {
        this.deliveryFileId = deliveryFileId;
    }

    public void setDestinationSiteId(int destinationSiteId) {
        this.destinationSiteId = destinationSiteId;
    }

    public void setSourceSiteId(int sourceSiteId) {
        this.sourceSiteId = sourceSiteId;
    }

    public TransportStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransportStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TransportRoute{" + "status=" + status + ", deliveryFileId=" + deliveryFileId + ", sourceSiteId="
                + sourceSiteId + ", destinationSiteId=" + destinationSiteId + "} " + super.toString();
    }
}
