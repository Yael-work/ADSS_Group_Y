package DB.implementations;

import Auxiliary.Helper;
import DB.interfaces.ITransportController;

import java.util.ArrayList;

import Models.DBModels.transport.Transport;

public class MockTransportController implements ITransportController {
    private static MockTransportController instance = null;
    private final ArrayList<Transport> transports;
    private int currentId = 1;

    private MockTransportController() {
        // Private constructor to prevent instantiation
        transports = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of MockTransportController.
     *
     * @return The singleton instance of MockTransportController.
     */
    public static MockTransportController getInstance() {
        if (instance == null) {
            instance = new MockTransportController();
        }
        return instance;
    }

    @Override
    public int addTransport(Transport transport) {
        if (transport == null) {
            return Helper.UndefinedId;
        }
        transport.setId(currentId++);
        transports.add(transport);
        return transport.getId();
    }

    @Override
    public boolean updateTransport(Transport transport, int id) {
        if (transport == null || id <= 0) {
            return false;
        }
        for (int i = 0; i < transports.size(); i++) {
            if (transports.get(i).getId() == id) {
                transports.set(i, transport);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteTransport(int id) {
        if (id <= 0) {
            return false;
        }
        for (int i = 0; i < transports.size(); i++) {
            if (transports.get(i).getId() == id) {
                transports.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public Transport getTransportById(int id) {
        if (id <= 0) {
            return null;
        }
        for (Transport transport : transports) {
            if (transport.getId() == id) {
                return transport;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Transport> getTransportsByTruckId(int id) {
        if (id <= 0) {
            return null;
        }
        ArrayList<Transport> result = new ArrayList<>();
        for (Transport transport : transports) {
            if (transport.getTruckId() == id) {
                result.add(transport);
            }
        }
        return result;
    }

    @Override
    public ArrayList<Transport> getTransportsByDriverId(int id) {
        if (id <= 0) {
            return null;
        }
        ArrayList<Transport> result = new ArrayList<>();
        for (Transport transport : transports) {
            if (transport.getDriverId() == id) {
                result.add(transport);
            }
        }
        return result;
    }

    @Override
    public final ArrayList<Transport> getAllRecords() {
        return new ArrayList<>(transports);
    }
}
