package Models.DBModels.transport;

import Models.DBModels.BaseModel;

public class Truck extends BaseModel {
    private String plate;
    private double netWeight;
    private double maxWeight;
    private String requiredLicenseType;
    private String model;

    public Truck(int id, String plate, double netWeight, double maxWeight, String requiredLicenseType, String model) {
        super(id, Truck.class.getName());
        this.plate = plate;
        this.netWeight = netWeight;
        this.maxWeight = maxWeight;
        this.requiredLicenseType = requiredLicenseType;
        this.model = model;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getRequiredLicenseType() {
        return requiredLicenseType;
    }

    public void setRequiredLicenseType(String requiredLicenseType) {
        this.requiredLicenseType = requiredLicenseType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "plate='" + plate + '\'' +
                ", netWeight=" + netWeight +
                ", maxWeight=" + maxWeight +
                ", requiredLicenseType='" + requiredLicenseType + '\'' +
                ", model='" + model + '\'' +
                "} " + super.toString();
    }
}
