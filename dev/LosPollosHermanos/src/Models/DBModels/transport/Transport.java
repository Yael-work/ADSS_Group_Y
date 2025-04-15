package Models.DBModels.transport;

import Models.DBModels.BaseModel;

import java.util.Date;
import java.util.List;

public class Transport extends BaseModel {
    private Date plannedDateTime;
    private TransportStatusEnum status;
    private int truckId;
    private int driverId;
    private List<Integer> sourceSiteIds;
    private List<Integer> destinationSiteIds;
    private List<Integer> transportIssueLogIds;
    private List<Integer> completedSourceSiteIds;
    private List<Integer> completedDestinationSiteIds;
    private Date actualStartDatetime;
    private Date actualEndDatetime;

    public Transport(int id, Date plannedDateTime, TransportStatusEnum status, int truckId, int driverId, List<Integer> sourceSiteIds, List<Integer> destinationSiteIds, List<Integer> transportIssueLogIds, List<Integer> completedSourceSiteIds, List<Integer> completedDestinationSiteIds, Date actualStartDatetime, Date actualEndDatetime) {
        super(id, Transport.class.getName());
        this.plannedDateTime = plannedDateTime;
        this.status = status;
        this.truckId = truckId;
        this.driverId = driverId;
        this.sourceSiteIds = sourceSiteIds;
        this.destinationSiteIds = destinationSiteIds;
        this.transportIssueLogIds = transportIssueLogIds;
        this.completedSourceSiteIds = completedSourceSiteIds;
        this.completedDestinationSiteIds = completedDestinationSiteIds;
        this.actualStartDatetime = actualStartDatetime;
        this.actualEndDatetime = actualEndDatetime;
    }

    public Date getPlannedDateTime() {
        return plannedDateTime;
    }

    public void setPlannedDateTime(Date plannedDateTime) {
        this.plannedDateTime = plannedDateTime;
    }

    public TransportStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransportStatusEnum status) {
        this.status = status;
    }

    public int getTruckId() {
        return truckId;
    }

    public void setTruckId(int truckId) {
        this.truckId = truckId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public List<Integer> getSourceSiteIds() {
        return sourceSiteIds;
    }

    public void setSourceSiteIds(List<Integer> sourceSiteIds) {
        this.sourceSiteIds = sourceSiteIds;
    }

    public List<Integer> getDestinationSiteIds() {
        return destinationSiteIds;
    }

    public void setDestinationSiteIds(List<Integer> destinationSiteIds) {
        this.destinationSiteIds = destinationSiteIds;
    }

    public List<Integer> getTransportIssueLogIds() {
        return transportIssueLogIds;
    }

    public void setTransportIssueLogIds(List<Integer> transportIssueLogIds) {
        this.transportIssueLogIds = transportIssueLogIds;
    }

    public List<Integer> getCompletedSourceSiteIds() {
        return completedSourceSiteIds;
    }

    public void setCompletedSourceSiteIds(List<Integer> completedSourceSiteIds) {
        this.completedSourceSiteIds = completedSourceSiteIds;
    }

    public List<Integer> getCompletedDestinationSiteIds() {
        return completedDestinationSiteIds;
    }

    public void setCompletedDestinationSiteIds(List<Integer> completedDestinationSiteIds) {
        this.completedDestinationSiteIds = completedDestinationSiteIds;
    }

    public Date getActualStartDatetime() {
        return actualStartDatetime;
    }

    public void setActualStartDatetime(Date actualStartDatetime) {
        this.actualStartDatetime = actualStartDatetime;
    }

    public Date getActualEndDatetime() {
        return actualEndDatetime;
    }

    public void setActualEndDatetime(Date actualEndDatetime) {
        this.actualEndDatetime = actualEndDatetime;
    }

    @Override
    public String toString() {
        return "Transport{" + "plannedDateTime='" + plannedDateTime + '\'' + ", status=" + status
                + ", truckId=" + truckId + ", driverId=" + driverId + ", sourceSiteIds=" + sourceSiteIds
                + ", destinationSiteIds=" + destinationSiteIds + ", transportIssueLogIds=" + transportIssueLogIds
                + ", completedSourceSiteIds=" + completedSourceSiteIds + ", completedDestinationSiteIds="
                + completedDestinationSiteIds + ", actualStartDatetime='" + actualStartDatetime + '\''
                + ", actualEndDatetime='" + actualEndDatetime + '\'' + "} " + super.toString();
    }
}
