package DB.implementations;

import DB.interfaces.ITransportAreaController;
import Models.DBModels.transport.TransportArea;

import java.util.ArrayList;

public class MockTransportAreaController implements ITransportAreaController {
    private static MockTransportAreaController instance = null;
    private final ArrayList<TransportArea> transportAreas;

    private MockTransportAreaController() {
        // Private constructor to prevent instantiation
        this.transportAreas = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of MockTransportAreaController.
     *
     * @return The singleton instance of MockTransportAreaController.
     */
    public static MockTransportAreaController getInstance() {
        if (instance == null) {
            instance = new MockTransportAreaController();
        }
        return instance;
    }

    @Override
    public boolean addTransportArea(TransportArea transportArea) {
        if (transportArea == null) {
            return false;
        }
        for (TransportArea area : transportAreas) {
            if (area.getId() == transportArea.getId()) {
                return false; // ID already exists
            }
        }
        transportAreas.add(transportArea);
        return true; // Successfully added
    }

    @Override
    public TransportArea getTransportAreaById(int id) {
        for (TransportArea area : transportAreas) {
            if (area.getId() == id) {
                return area; // Found the transport area
            }
        }
        return null; // Not found
    }

    @Override
    public boolean updateTransportArea(TransportArea transportArea, int id) {
        if(transportArea == null || id <= 0) {
            return false;
        }
        for (int i = 0; i < transportAreas.size(); i++) {
            if (transportAreas.get(i).getId() == id) {
                transportAreas.set(i, transportArea);
                return true; // Successfully updated
            }
        }
        return false; // Not found
    }

    @Override
    public boolean deleteTransportArea(int id) {
        for (int i = 0; i < transportAreas.size(); i++) {
            if (transportAreas.get(i).getId() == id) {
                transportAreas.remove(i);
                return true; // Successfully deleted
            }
        }
        return false; // Not found
    }
}
