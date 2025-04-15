package Models.DBModels.transport;

import Models.DBModels.BaseModel;

public class TransportArea extends BaseModel {
    private String areaName;

    public TransportArea(int id, String areaName) {
        super(id, TransportArea.class.getName());
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
