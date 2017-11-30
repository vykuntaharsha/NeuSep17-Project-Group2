package src.finalproject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class VehicleManagerAlternateImplementation implements VehicleManagerBehaviors {
    private Map<String, Vehicle> vehicles;
    public VehicleManagerAlternateImplementation(Map <String, Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public Collection<Vehicle> getVehicles() {
        return new ArrayList<Vehicle>(vehicles.values());
    }

    public Collection<Vehicle> getpartVehicles(int begin, int end) {
        ArrayList<Vehicle> res = new ArrayList<Vehicle>(vehicles.values());
        if(begin>res.size()) return null;
        if(end>res.size()) end = res.size();
        return res.subList(begin,end);
    }

    public Map<String, Vehicle> getmap(){
        return vehicles;
    }

    @Override
    public int getTotalVehicle() {
        return vehicles.size();
    }

    @Override
    public void saveVehicle(Vehicle vehicle) {
        vehicles.put(vehicle.getId(),vehicle);
    }

    @Override
    public void editVehicle() {

    }

    @Override
    public void deleteVehicle(String roll) {
        if(!vehicles.containsKey(roll)) return;
        vehicles.remove(roll);
    }

    @Override
    public void displayVehicles() {

    }

    @Override
    public void save() {

    }
}
