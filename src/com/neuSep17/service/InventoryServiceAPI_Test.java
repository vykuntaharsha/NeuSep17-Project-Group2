package com.neuSep17.service;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import java.net.MalformedURLException;

import java.awt.Image;

import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.neuSep17.dto.Vehicle;
import com.neuSep17.dao.InventoryDAO;


public class InventoryServiceAPI_Test {
	
	private InventoryDAO inventoryDAO;
	
	public InventoryServiceAPI_Test() {
		this.inventoryDAO = new InventoryDAO();	
	}
	
	public InventoryServiceAPI_Test(String file) {
		this.inventoryDAO = new InventoryDAO(file);	
	}
	
	

	public ArrayList<Vehicle> getVehicles() {
		return inventoryDAO.getVehicles();
	}
	

	public String getFileName() {
		return inventoryDAO.getFileName();
	}


	public void saveInventoryToFile() {
		inventoryDAO.saveInventoryToFile();
	}

	
	public LinkedHashMap<String, Vehicle> getVehiclesMap() {
		return inventoryDAO.getVehiclesMap();
	}
	

	public int getTotalVehicleAmount() {
		return inventoryDAO.getVehiclesMap().size();
	}
	
	
	public Vehicle createVehicleFromInput(String[] vehicleData) {
		Vehicle vehicle = new Vehicle(vehicleData);
		return vehicle;
	}
	

	public void editVehicle(String vin, int vehicleDataIndex, String vehicleDataEditText) throws MalformedURLException {
		inventoryDAO.editVehicle(vin, vehicleDataIndex, vehicleDataEditText);
	}

	
	public void addVehicle(Vehicle vehicle) {
		inventoryDAO.getVehiclesMap().put(vehicle.getId(), vehicle);

	}

	
	public void deleteVehicle(String vin) {
		inventoryDAO.getVehiclesMap().remove(vin);
		
	}

	
	public static ArrayList<Image> getVehicleImage(String bodyType) throws IOException {
		return InventoryDAO.getVehicleImage(bodyType);
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
		//return vehiclesMap.get(id);
		return inventoryDAO.getVehiclesMap().get(id);
	}
	

	/**
	 * Pass in a list of vehicles and different fields to filter and return the filtered result. Filter 
	 * fields of category, year, make, price and type can accept multiple values and should seperate their 
	 * values by ";", e.g. "new;used". The price values should be a range and the lower limit (inclusive) 
	 * and upper limit (exclusive) should be connected with a "~". If the field is null, then the filter 
	 * of that field is not applied.
	 * @param vehicles list to be sorted
	 * @param category desired category of the vehicles
	 * @param year desired years of the vehicles
	 * @param make desired makes of the vehicles
     * @param model desired model of the vehicles
	 * @param price desired price range of the vehicles
	 * @param type desired types of the vehicles
	 * @param search search keywords for the vehicles
	 * @return the filtered list of vehicles.
	 */
	public static List<Vehicle> vehiclesSearchAndFilter(List<Vehicle> vehicles,  String category, String year, String make, String price,
	String type, String search){
		List<Vehicle> filteredVehicles = new ArrayList<Vehicle>();
		// if (vehicles == null) {
			
		// }

		for (Vehicle vehicle : vehicles) {
			if (categoryFilter(vehicle, category) && yearFilter(vehicle, year) && makeFilter(vehicle, make) && priceFilter(vehicle, price) && typeFilter(vehicle, type) && searchFilter(vehicle, search)) {
				filteredVehicles.add(vehicle);
			}
		}
		return filteredVehicles;
	}

    public static List<Vehicle> vehiclesSearchAndFilter_test(List<Vehicle> vehicles,  String category, String year, String make, String model, String price,
                                                        String type, String search){
        List<Vehicle> filteredVehicles = new ArrayList<Vehicle>();
        // if (vehicles == null) {

        // }

        for (Vehicle vehicle : vehicles) {
            if (categoryFilter(vehicle, category) && yearFilter(vehicle, year)  && makeFilter(vehicle, make) && modelFilter(vehicle, model) && priceFilter(vehicle, price) && typeFilter(vehicle, type) && searchFilter(vehicle, search)) {
                filteredVehicles.add(vehicle);
            }
        }
        return filteredVehicles;
    }

	private static boolean categoryFilter(Vehicle vehicle, String category) {
		//test
		if (category == null || category.equals(""))
			return true;
		return category.contains(vehicle.getCategory().toString().toLowerCase());
	}

	private static boolean yearFilter(Vehicle vehicle, String year) {
		if (year == null || year.equals(""))
			return true;
		return year.contains(vehicle.getYear().toString());
	}

	private static boolean makeFilter(Vehicle vehicle, String make) {
		if (make == null || make.equals(""))
			return true;
		String target = vehicle.getMake();
		String[] makes = make.split(";");
		for (String s : makes) {
			if (s.equals(target))
				return true;
		}
		return false;
	}

    private static boolean modelFilter(Vehicle vehicle, String model) {
        if (model == null || model.equals(""))
            return true;
        return vehicle.getModel().toString().contains(model);
    }

	private static boolean priceFilter(Vehicle vehicle, String price) {
		if (price == null || price.equals(""))
			return true;
		float vehiclePrice = vehicle.getPrice();
		String[] priceRanges = price.split(";");
		for (String priceRange : priceRanges) {
			String[] limits = priceRange.split("~");
			float low = Float.parseFloat(limits[0]);
			if (limits.length == 1) {
				if (vehiclePrice >= low)
					return true;
			} else {
				float high = Float.parseFloat(limits[1]);
				if (vehiclePrice >= low && vehiclePrice < high)
					return true;
			}
		}
		return false;
	}

	private static boolean typeFilter(Vehicle vehicle, String type) {
		if (type == null || type.equals(""))
			return true;
		String target = vehicle.getBodyType();
		String [] types = type.split(";");
		for (String s : types) {
			if(s.equals(target))
				return true;
		}
		return false;
	}

	private static boolean searchFilter(Vehicle vehicle, String search) {
		if (search == null || search.equals(""))
			return true;
		search = search.toLowerCase();
		String vehicleString = vehicle.toSearchContent().toLowerCase();
		String [] arr = search.split("\\s+");
		for (String word : arr) {
			if(!vehicleString.contains(word))
				return false;
		}
		return true;
	}

	/**
	 * Take in a list of vehicles and return a map. The keys of the map are "category", "year", "make",
	 * "price" and "type". The value are maps whose keys are sorted corresponding values contained by 
	 * the passed in list of vehicles and the corresponding keys are their counts.
	 * @param vehicles list of vehicles passed in
	 * @return the result map
	 */
	public static Map<String, Map<String, Integer>> getComboBoxItemsMap(List<Vehicle> vehicles) {
		TreeMap<String, Integer> categoryMap = new TreeMap<>();
		TreeMap<String, Integer> yearMap = new TreeMap<>();
		TreeMap<String, Integer> makeMap = new TreeMap<>();
		TreeMap<String, Integer> priceMap = new TreeMap<>();
		TreeMap<String, Integer> typeMap = new TreeMap<>();
		TreeMap<String, Integer> modelMap = new TreeMap<>();
		for (Vehicle vehicle : vehicles) {
			String category = vehicle.getCategory().toString().toLowerCase();
			String year = vehicle.getYear().toString();
			String make = vehicle.getMake();
			String type = vehicle.getBodyType();
			String price = priceToString(vehicle.getPrice());
			String model = vehicle.getModel();
			addToCountMap(categoryMap, category);
			addToCountMap(yearMap, year);
			addToCountMap(makeMap, make);
			addToCountMap(priceMap, price);
			addToCountMap(typeMap, type);
			addToCountMap(modelMap, model);
		}
		Map<String, Map<String, Integer>> map = new HashMap<>();
		map.put("category", categoryMap);
		map.put("year", yearMap);
		map.put("make", makeMap);
		map.put("price", priceMap);
		map.put("type", typeMap);
		map.put("model", modelMap);
		return map;
	}

	private static String priceToString(float price) {
		for (int i = 0; i < 10; i++) {
			int low = i * 10000;
			int high = (i + 1) * 10000;
			if (price >= low && price < high) {
				return low + "~" + high;
			}
		}
		return "100000~";
	}

	private static void addToCountMap(Map<String, Integer> map, String item) {
		if (map.containsKey(item))
			map.put(item, map.get(item) + 1);
		else
			map.put(item, 1);
	}

	/**
	 * Take in a list of vehicles and sort it according to the sortType.
	 * @param vehicles list of vehicles passed in
	 * @param sortType the field according to which the list should be sorted
	 * @param isAscending true if the list is to be sorted ascendingly and false if it is to be sorted 
	 * descendingly
	 * @return the sorted list
	 */
	public static List<Vehicle> sortVehicles(List<Vehicle> vehicles, String sortType, boolean isAscending) {
		switch (sortType) {
		case "price":
			sortByPrice(vehicles);
			break;
		case "year":
			sortByYear(vehicles);
			break;
		case "make":
			sortByMake(vehicles);
			break;
		default:
			break;
		}
		if (isAscending == false)
			Collections.reverse(vehicles);
		return vehicles;
	}

	private static void sortByPrice(List<Vehicle> vehicles){
		vehicles.sort(new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle v1, Vehicle v2) {
				float diff = v1.getPrice() - v2.getPrice();
				if (diff > 0) {
					return 1;
				} else if (diff == 0) {
					return 0;
				} else {
					return -1;
				}
			}
		});
	}

	private static void sortByYear(List<Vehicle> vehicles){
		vehicles.sort(new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle v1, Vehicle v2) {
				return v1.getYear() - v2.getYear();
			}
		});
	}

	private static void sortByMake(List<Vehicle> vehicles){
		vehicles.sort(new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle v1, Vehicle v2) {
				return v1.getMake().compareTo(v2.getMake());
			}
		});
	}

}
