package Models.DBModels.transport;

import Models.DBModels.BaseModel;
import jakarta.persistence.*;

@Entity
@Table(name = "sites")
public class Site extends BaseModel {
    @OneToOne
    @JoinColumn(name = "site_object_id")
    private APhysicalSite physicalSite;
    @Column(nullable = false)
    private String siteAddress;
    @Column(nullable = false)
    private String contactPhone;
    @Column(nullable = false)
    private String contactName;
    @Column()
    private double recordedWeightAtSite; //optional

    public Site(int id, APhysicalSite physicalSite, String siteAddress,
                String contactPhone, String contactName, double recordedWeightAtSite) {
        super(id, Site.class.getName());
        if (physicalSite == null) {
            throw new IllegalArgumentException(String.format("%s::physicalSite cannot be null", this.getClass().getName()));
        }
        this.physicalSite = physicalSite;
        this.siteAddress = siteAddress;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.recordedWeightAtSite = recordedWeightAtSite;
    }

    public Site() {
        super(0, Site.class.getName());
    }

    public APhysicalSite getPhysicalSite() {
        return physicalSite;
    }

    public void setPhysicalSite(APhysicalSite physicalSite) {
        if (physicalSite == null) {
            throw new IllegalArgumentException(String.format("%s::physicalSite cannot be null", this.getClass().getName()));
        }
        this.physicalSite = physicalSite;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public double getRecordedWeightAtSite() {
        return recordedWeightAtSite;
    }

    public void setRecordedWeightAtSite(double recordedWeightAtSite) {
        this.recordedWeightAtSite = recordedWeightAtSite;
    }

    @Override
    public String toString() {
        return "Site{" +
                "physicalSite=" + physicalSite +
                ", siteAddress='" + siteAddress + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactName='" + contactName + '\'' +
                ", recordedWeightAtSite=" + recordedWeightAtSite +
                "} " + super.toString();
    }

    @Override
    public String toPrettyString() {
        return """
                Site ID: %d
                Site object: %s
                Site Address: %s
                Contact Phone: %s
                Contact Name: %s
                Recorded Weight At Site: %.2f
                """.formatted(getId(), physicalSite, siteAddress, contactPhone,
                contactName, recordedWeightAtSite);
    }

}
