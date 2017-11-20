Dealer.javapackage com.neuSep17.service;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.neuSep17.dto.Vehicle;


public class ServiceAPI_Test {

	private LinkedHashMap<String, Vehicle> vehiclesMap;
	private String fileName;


	public ArrayList<Vehicle> getVehicles() {
		return new ArrayList<Vehicle>(vehiclesMap.values());
	}
	

	public String getFileName() {
		return fileName;
	}


	public ServiceAPI_Test(String file) {
		this.fileName = file;
		try {
			vehiclesMap = getVehiclesMap(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	public void saveToFile() {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(new File(fileName)));
			writer.println("id~webId~category~year~make~model~trim~type~price~photo");
			for (Vehicle vehicle: vehiclesMap.values()) {
				writer.println(vehicle);
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static LinkedHashMap<String, Vehicle> getVehiclesMap(String file) throws IOException {
		File inventoryFile = new File(file);
		BufferedReader reader = new BufferedReader(new FileReader(inventoryFile));
		LinkedHashMap<String, Vehicle> vehicles = new LinkedHashMap<String, Vehicle>();		
		String line = null;
		int count = 0;
		while((line=reader.readLine())!=null) {
			count++;
			if(count==1) {continue;}
			String[] vehicleDataArray = line.split("~");
			Vehicle vehicle =new Vehicle(vehicleDataArray);
			vehicles.put(vehicle.getId(), vehicle);
		}
		
		reader.close();
		return vehicles;

	}

	
	public int getTotalVehicleAmount() {
		return vehiclesMap.size();
	}
	
	
	public Vehicle createVehicleFromInput(String[] vehicleData) {
		Vehicle vehicle = new Vehicle(vehicleData);
		return vehicle;
	}

	
	public void addVehicle(Vehicle vehicle) {
		vehiclesMap.put(vehicle.getId(), vehicle);

	}

	
	public void deleteVehicle(String vin) {
		vehiclesMap.remove(vin);	
		
	}
	
	
	public void search(String text, TableRowSorter<TableModel> sorter) { 
		if (text.length() == 0) {
			sorter.setRowFilter(null);
		} 
		else {
			// (?i) regex flag: case-insensitive matching
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
		}
	}
	
	
  public void cancelSearch(TableRowSorter<TableModel> sorter) {
		sorter.setRowFilter(null);
	}
	
	
	public void sortByColumn(int columnIndexToSort, TableRowSorter<TableModel> sorter, int Asc_Desc) {
		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
		
		switch (Asc_Desc) {
		case 0:
			sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));break;
		case 1:
			sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));break;
		case -1:
			sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.UNSORTED));break;
		}

		sorter.setSortKeys(sortKeys);
		sorter.sort();
	}

	
  // To be continued...

	public Vehicle getVehicleDetails(String id){
		return vehiclesMap.get(id);
	}
	
	
}
