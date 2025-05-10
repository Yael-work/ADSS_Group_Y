package Models.DBModels.transport;

import Auxiliary.Enums.Models.TransportStatusEnum;
import Models.DBModels.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transport_routes")
public class TransportRoute extends BaseModel {
    @Column(nullable = false)
    private TransportStatusEnum status;
    @OneToOne
    @Column(nullable = false)
    private DeliveryFile deliveryFile;
    @OneToOne
    @Column(nullable = false)
    private APhysicalSite sourceSite;
    @OneToOne
    @Column(nullable = false)
    private APhysicalSite destinationSite;

    @Column(nullable = false)
    private String sourceContactName;
    @Column(nullable = false)
    private String sourceContactPhone;
    @Column(nullable = false)
    private String destinationContactName;
    @Column(nullable = false)
    private String destinationContactPhone;

    @Column()
    private double recordedWeightAtSite;

    public TransportRoute(int id, TransportStatusEnum status, DeliveryFile deliveryFile, APhysicalSite sourceSite,
                          APhysicalSite destinationSite, String sourceContactName, String sourceContactPhone,
                          String destinationContactName, String destinationContactPhone, double recordedWeightAtSite) {
        super(id, TransportRoute.class.getName());
        this.status = status;
        this.deliveryFile = deliveryFile;
        this.sourceSite = sourceSite;
        this.destinationSite = destinationSite;
        this.sourceContactName = sourceContactName;
        this.sourceContactPhone = sourceContactPhone;
        this.destinationContactName = destinationContactName;
        this.destinationContactPhone = destinationContactPhone;
        this.recordedWeightAtSite = recordedWeightAtSite;
    }

    public TransportRoute() {
        super(0, TransportRoute.class.getName());
        this.status = TransportStatusEnum.PENDING;
    }

    public TransportStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransportStatusEnum status) {
        this.status = status;
    }

    public DeliveryFile getDeliveryFile() {
        return deliveryFile;
    }

    public void setDeliveryFile(DeliveryFile deliveryFile) {
        this.deliveryFile = deliveryFile;
    }

    public APhysicalSite getSourceSite() {
        return sourceSite;
    }

    public void setSourceSite(APhysicalSite sourceSite) {
        this.sourceSite = sourceSite;
    }

    public APhysicalSite getDestinationSite() {
        return destinationSite;
    }

    public void setDestinationSite(APhysicalSite destinationSite) {
        this.destinationSite = destinationSite;
    }

    public String getSourceContactName() {
        return sourceContactName;
    }

    public void setSourceContactName(String sourceContactName) {
        this.sourceContactName = sourceContactName;
    }

    public String getSourceContactPhone() {
        return sourceContactPhone;
    }

    public void setSourceContactPhone(String sourceContactPhone) {
        this.sourceContactPhone = sourceContactPhone;
    }

    public String getDestinationContactName() {
        return destinationContactName;
    }

    public void setDestinationContactName(String destinationContactName) {
        this.destinationContactName = destinationContactName;
    }

    public String getDestinationContactPhone() {
        return destinationContactPhone;
    }

    public void setDestinationContactPhone(String destinationContactPhone) {
        this.destinationContactPhone = destinationContactPhone;
    }

    public double getRecordedWeightAtSite() {
        return recordedWeightAtSite;
    }

    public void setRecordedWeightAtSite(double recordedWeightAtSite) {
        this.recordedWeightAtSite = recordedWeightAtSite;
    }


    @Override
    public String toString() {
        return "TransportRoute{" +
                "status=" + status +
                ", deliveryFile=" + deliveryFile +
                ", sourceSite=" + sourceSite +
                ", destinationSite=" + destinationSite +
                ", sourceContactName='" + sourceContactName + '\'' +
                ", sourceContactPhone='" + sourceContactPhone + '\'' +
                ", destinationContactName='" + destinationContactName + '\'' +
                ", destinationContactPhone='" + destinationContactPhone + '\'' +
                ", recordedWeightAtSite=" + recordedWeightAtSite +
                "} " + super.toString();
    }

    @Override
    public boolean copyFromOther(BaseModel other) {
        if (other instanceof TransportRoute otherTransportRoute) {
            this.status = otherTransportRoute.status;
            this.deliveryFile = otherTransportRoute.deliveryFile;
            this.sourceSite = otherTransportRoute.sourceSite;
            this.destinationSite = otherTransportRoute.destinationSite;
            this.sourceContactName = otherTransportRoute.sourceContactName;
            this.sourceContactPhone = otherTransportRoute.sourceContactPhone;
            this.destinationContactName = otherTransportRoute.destinationContactName;
            this.destinationContactPhone = otherTransportRoute.destinationContactPhone;
            this.recordedWeightAtSite = otherTransportRoute.recordedWeightAtSite;
            return true;
        }
        return false;
    }
}
