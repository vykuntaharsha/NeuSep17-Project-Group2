package src.finalproject;

import java.util.Collection;

public interface VehicleManagerBehaviors {
    public Collection<Vehicle> getVehicles();

    int getTotalVehicle();

    void saveVehicle(Vehicle vehicle);

    void editVehicle();

    void deleteVehicle(String roll);

    void displayVehicles();

    void save();
}
