package Models.DBModels;

import java.util.List;

public class DeliveryFile extends BaseModel {
    private List<Integer> deliveryFileItemIds; // FK IDs

    public DeliveryFile(int id, List<Integer> deliveryFileItemIds) {
        super(id, DeliveryFile.class.getName());
        this.deliveryFileItemIds = deliveryFileItemIds;
    }

    public List<Integer> getDeliveryFileItemIds() {
        return deliveryFileItemIds;
    }

    public void setDeliveryFileItemIds(List<Integer> deliveryFileItemIds) {
        this.deliveryFileItemIds = deliveryFileItemIds;
    }

    @Override
    public String toString() {
        return "DeliveryFile{" +
                "deliveryFileItemIds=" + deliveryFileItemIds +
                "} " + super.toString();
    }
}
