package Models.DBModels.transport;

import Models.DBModels.BaseModel;

import java.util.List;

public class TransportArea extends BaseModel {
    private String areaName;
    private List<Integer> suppliersIds; // FK IDs
    private List<Integer> branchesIds; // FK IDs


    public TransportArea(int id, String areaName, List<Integer> suppliersIds, List<Integer> branchesIds) {
        super(id, TransportArea.class.getName());
        this.areaName = areaName;
        this.suppliersIds = suppliersIds;
        this.branchesIds = branchesIds;
    }

    public List<Integer> getSuppliersIds() {
        return suppliersIds;
    }

    public void setSuppliersIds(List<Integer> suppliersIds) {
        this.suppliersIds = suppliersIds;
    }

    public List<Integer> getBranchesIds() {
        return branchesIds;
    }

    public void setBranchesIds(List<Integer> branchesIds) {
        this.branchesIds = branchesIds;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return "TransportArea{" +
                "areaName='" + areaName + '\'' +
                ", suppliersIds=" + suppliersIds +
                ", branchesIds=" + branchesIds +
                "} " + super.toString();
    }

    @Override
    public boolean copyFromOther(BaseModel other) {
        if (other instanceof TransportArea otherTransportArea) {
            this.areaName = otherTransportArea.areaName;
            this.suppliersIds = otherTransportArea.suppliersIds;
            this.branchesIds = otherTransportArea.branchesIds;
            return true;
        }
        return false;
    }
}
