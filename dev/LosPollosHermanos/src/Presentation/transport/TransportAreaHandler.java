package Presentation.transport;

import Auxiliary.Helper;
import Models.DBModels.transport.TransportArea;
import DB.interfaces.ITransportAreaController;

import java.util.ArrayList;
import java.util.List;

public class TransportAreaHandler {
    public static boolean addTransportArea() {
        String areaName;
        List<Integer> suppliersIds; // Need to be initialized
        List<Integer> branchesIds;
        try {
            Helper.println("Transport Area creation:");
            S
            do {
                Helper.println("Enter Transport Area name: ");
                name = Helper.getUserInputString();
            } while (name == null || name.isEmpty());
            do {
                Helper.println("Enter Transport Area location: ");
                location = Helper.getUserInputString();
            } while (location == null || location.isEmpty());
            do {
                Helper.println("Enter Transport Area capacity: ");
                capacity = Helper.getUserInputInt();
            } while (capacity <= 0);
        } catch (Exception e) {
            Helper.showError("Error: " + e.getMessage());
            return false;
        }
        boolean f = ITransportAreaController.addTransportArea(name, location, capacity);
        if (!f) {
            Helper.showError("Transport Area with this name already exists");
            return false;
        }
        return true;
    }
}
