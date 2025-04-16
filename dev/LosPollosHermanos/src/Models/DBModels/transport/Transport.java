package Models.DBModels.transport;

import Models.DBModels.BaseModel;
import Auxiliary.Enums.Models.TransportStatusEnum;

import java.util.Date;
import java.util.List;

public class Transport extends BaseModel {
    private Date plannedDateTime;
    private TransportStatusEnum status;
    private int truckId;
    private int driverId;
    private List<Integer> transportRouteIds;
    private List<Integer> transportIssueLogIds;
    private Date actualStartDatetime;
    private Date actualEndDatetime;

    public Transport(int id, Date plannedDateTime, TransportStatusEnum status, int truckId, int driverId,
                     List<Integer> transportRouteIds, List<Integer> transportIssueLogIds,
                     Date actualStartDatetime, Date actualEndDatetime) {
        super(id, Transport.class.getName());
        this.plannedDateTime = plannedDateTime;
        this.status = status;
        this.truckId = truckId;
        this.driverId = driverId;
        this.transportIssueLogIds = transportIssueLogIds;
        this.transportRouteIds = transportRouteIds;
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

    public List<Integer> getTransportIssueLogIds() {
        return transportIssueLogIds;
    }

    public void setTransportIssueLogIds(List<Integer> transportIssueLogIds) {
        this.transportIssueLogIds = transportIssueLogIds;
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

    public void setTransportRouteIds(List<Integer> transportRouteIds) {
        this.transportRouteIds = transportRouteIds;
    }

    public List<Integer> getTransportRouteIds() {
        return transportRouteIds;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "plannedDateTime=" + plannedDateTime +
                ", status=" + status +
                ", truckId=" + truckId +
                ", driverId=" + driverId +
                ", transportRouteIds=" + transportRouteIds +
                ", transportIssueLogIds=" + transportIssueLogIds +
                ", actualStartDatetime=" + actualStartDatetime +
                ", actualEndDatetime=" + actualEndDatetime +
                "} " + super.toString();
    }
}
