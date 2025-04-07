package Models.DBModels;


public class Site extends BaseModel {
    private SiteTypeEnum siteType;
    private int siteObjectId;
    private String siteAddress;
    private String contactPhone;
    private String contactName;
    private double recordedWeightAtSite; //optional
    private int DeliveryFileId; // optional

    public Site(int id, SiteTypeEnum siteType, int siteObjectId, String siteAddress,
                String contactPhone, String contactName, double recordedWeightAtSite, int deliveryFileId) {
        super(id, Site.class.getName());
        if (siteObjectId <= 0) {
            throw new IllegalArgumentException(String.format("%s::SiteObjectId cannot be less than or equal to 0",
                    this.getClass().getName()));
        }
        this.siteType = siteType;
        this.siteObjectId = siteObjectId;
        this.siteAddress = siteAddress;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.recordedWeightAtSite = recordedWeightAtSite;
        this.DeliveryFileId = deliveryFileId;
    }

    public SiteTypeEnum getSiteType() {
        return siteType;
    }

    public void setSiteType(SiteTypeEnum siteType) {
        this.siteType = siteType;
    }

    public int getSiteObjectId() {
        return siteObjectId;
    }

    public void setSiteObjectId(int siteObjectId) {
        if (siteObjectId <= 0) {
            throw new IllegalArgumentException(String.format("%s::SiteObjectId cannot be less than or equal to 0",
                    this.getClass().getName()));
        }
        this.siteObjectId = siteObjectId;
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

    public int getDeliveryFileId() {
        return DeliveryFileId;
    }

    public void setDeliveryFileId(int deliveryFileId) {
        DeliveryFileId = deliveryFileId;
    }

    @Override
    public String toString() {
        return "Site{" +
                "siteType=" + siteType +
                ", siteObjectId=" + siteObjectId +
                ", siteAddress='" + siteAddress + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactName='" + contactName + '\'' +
                ", recordedWeightAtSite=" + recordedWeightAtSite +
                ", DeliveryFileId=" + DeliveryFileId +
                "} " + super.toString();
    }


}
