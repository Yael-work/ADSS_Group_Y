package Models.DBModels.transport;

import Models.DBModels.BaseModel;
import Auxiliary.Enums.Models.TransportStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public class Transport extends BaseModel {
    private LocalDateTime plannedDateTime;
    private TransportStatusEnum status;
    private int truckId;
    private int driverId;
    private List<Integer> transportRouteIds;
    private List<Integer> transportIssueLogIds;
    private LocalDateTime actualStartDatetime;
    private LocalDateTime actualEndDatetime;

    public Transport(int id, LocalDateTime plannedDateTime, TransportStatusEnum status, int truckId, int driverId,
                     List<Integer> transportRouteIds, List<Integer> transportIssueLogIds,
                     LocalDateTime actualStartDatetime, LocalDateTime actualEndDatetime) {
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

    public LocalDateTime getPlannedDateTime() {
        return plannedDateTime;
    }

    public void setPlannedDateTime(LocalDateTime plannedDateTime) {
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

    public LocalDateTime getActualStartDatetime() {
        return actualStartDatetime;
    }

    public void setActualStartDatetime(LocalDateTime actualStartDatetime) {
        this.actualStartDatetime = actualStartDatetime;
    }

    public LocalDateTime getActualEndDatetime() {
        return actualEndDatetime;
    }

    public void setActualEndDatetime(LocalDateTime actualEndDatetime) {
        this.actualEndDatetime = actualEndDatetime;
    }

    public void setTransportRouteIds(List<Integer> transportRouteIds) {
        this.transportRouteIds = transportRouteIds;
    }

    public List<Integer> getTransportRouteIds() {
        return this.transportRouteIds;
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

    @Override
    public String toPrettyString() {
        return String.format("""
                        ID: %d
                        Planned DateTime: %s
                        Status: %s
                        Truck ID: %d
                        Driver ID: %d
                        Transport Route IDs: %s
                        Transport Issue Log IDs: %s
                        Actual Start DateTime: %s
                        Actual End DateTime: %s
                        """,
                getId(), plannedDateTime, status, truckId, driverId,
                transportRouteIds.toString(), transportIssueLogIds != null ? transportIssueLogIds.toString()
                        : "[No issue logs]"
                , actualStartDatetime, actualEndDatetime);
    }
}
