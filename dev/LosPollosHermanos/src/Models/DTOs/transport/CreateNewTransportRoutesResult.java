package Models.DTOs.transport;

import Auxiliary.Enums.Domain.CreateNewTransportRouteResultEnum;
import Models.DTOs.StockItem;

import java.util.List;

public class CreateNewTransportRoutesResult {
    private List<Integer> createdTransportRoutesIds;
    private CreateNewTransportRouteResultEnum resultEnum;
    private List<StockItem> unprovidedItems;

    public CreateNewTransportRoutesResult(List<Integer> createdTransportRoutesIds,
                                          CreateNewTransportRouteResultEnum resultEnum,
                                          List<StockItem> unprovidedItems) {
        this.createdTransportRoutesIds = createdTransportRoutesIds;
        this.resultEnum = resultEnum;
        this.unprovidedItems = unprovidedItems;
    }

    public List<StockItem> getUnprovidedItems() {
        return unprovidedItems;
    }

    public void setUnprovidedItems(List<StockItem> unprovidedItems) {
        this.unprovidedItems = unprovidedItems;
    }

    public List<Integer> getCreatedTransportRoutesIds() {
        return createdTransportRoutesIds;
    }

    public void setCreatedTransportRoutesIds(List<Integer> createdTransportRoutesIds) {
        this.createdTransportRoutesIds = createdTransportRoutesIds;
    }

    public CreateNewTransportRouteResultEnum getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(CreateNewTransportRouteResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }

}
