package com.neuSep17.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;

import com.neuSep17.dto.Inventory;
import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.DealerAPI;
import com.neuSep17.service.InventoryServiceAPI_Test;

public class InventoryAPI extends DealerAPI{
	

	private String dataFolderPath;
	public LinkedHashMap<String, Inventory> dealerInventoryMap;
	
	public InventoryAPI(String dataFolderPath) throws IOException {
		super(dataFolderPath+"/dealers");
		this.getAllInventory(this.getDealerMap().keySet());
		
	}
	
	public LinkedHashMap<String, Inventory> getAllInventory(Collection<String> dealerIds) throws IOException  {
		LinkedHashMap<String, Inventory> dealerInventory = new LinkedHashMap<>();
		for (String dealerId : dealerIds) {
			Inventory inventory = new Inventory();
			LinkedHashMap<String, Vehicle> dealerVehicles = InventoryServiceAPI_Test.getVehiclesMap(dataFolderPath+dealerId);
			inventory.setDealerId(dealerId);
			inventory.setVehicles(dealerVehicles.values());
			dealerInventory.put(dealerId, inventory);
		}
		return dealerInventory;
	}
	
	public void addVehicleToDealersInventory(String dealerId, Vehicle vehicle) {
		Inventory dealerInventory = dealerInventoryMap.get(dealerId);
		ArrayList<Vehicle> vehicleList = (ArrayList<Vehicle>) dealerInventory.getVehicles();
		vehicleList.add(vehicle);
		dealerInventoryMap.put(dealerId, dealerInventory);
	}
	
	public boolean removeVehicleFromDealersInventory(String dealerId, String vehicleId) {
		Inventory dealerInventory = dealerInventoryMap.get(dealerId);
		ArrayList<Vehicle> vehicleList = (ArrayList<Vehicle>) dealerInventory.getVehicles();
		for(int i=0 ; i < vehicleList.size(); i++) {
			if(vehicleList.get(i).getId().equals(vehicleId)) {
				vehicleList.remove(i);
				dealerInventoryMap.put(dealerId, dealerInventory);
				return true;
			}
		}
		return false;
	}
		
	public boolean updateVehicleInDealersInventory(String dealerId, Vehicle vehicle) {
		boolean hasVehicle = removeVehicleFromDealersInventory(dealerId, vehicle.getId());
		if(!hasVehicle)
			return false;
		else
			addVehicleToDealersInventory(dealerId, vehicle);
		return true;
	}
}
