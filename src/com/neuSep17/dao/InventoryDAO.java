package com.neuSep17.dao;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.imageio.ImageIO;

import com.neuSep17.dto.Category;
import com.neuSep17.dto.Vehicle;

public class InventoryDAO {
	
	
	
	private LinkedHashMap<String, Vehicle> vehiclesMap;
	private LinkedHashMap<String, ArrayList<Image>> vehicleImagesMap;
	private String fileName;
	
	public InventoryDAO(String file) {
		this.fileName = file;
		try {
			vehiclesMap = getVehiclesMap(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		try {
			vehicleImagesMap = getVehicleImagesMap();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public LinkedHashMap<String, Vehicle> getVehiclesMap() {
		return vehiclesMap;
	}
	
	public ArrayList<Vehicle> getVehicles() {
		return new ArrayList<Vehicle>(vehiclesMap.values());
	}
	

	public String getFileName() {
		return fileName;
	}
	
	
	public void saveInventoryToFile() {
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
		LinkedHashMap<String, Vehicle> vehiclesMap = new LinkedHashMap<String, Vehicle>();		
		String line = null;
		int count = 0;
		while((line=reader.readLine())!=null) {
			count++;
			if(count==1) {continue;}
			String[] vehicleDataArray = line.split("~");
			Vehicle vehicle =new Vehicle(vehicleDataArray);
			vehiclesMap.put(vehicle.getId(), vehicle);
		}
		
		reader.close();
		return vehiclesMap;

	}

	
	
	public int getTotalVehicleAmount() {
		return vehiclesMap.size();
	}
	
	
	public Vehicle createVehicleFromInput(String[] vehicleData) {
		Vehicle vehicle = new Vehicle(vehicleData);
		return vehicle;
	}
	
	
	public void editVehicle(String vin, int vehicleDataIndex, String vehicleDataEditText) throws MalformedURLException {
		switch(vehicleDataIndex) {
			case 2: vehiclesMap.get(vin).setCategory(Category.getCategory(vehicleDataEditText.trim().toLowerCase()));break;
			case 3: vehiclesMap.get(vin).setYear(Integer.parseInt(vehicleDataEditText.trim()));break;
			case 4: vehiclesMap.get(vin).setMake(vehicleDataEditText.trim());break;
			case 5: vehiclesMap.get(vin).setModel(vehicleDataEditText.trim());break;
			case 6: vehiclesMap.get(vin).setTrim(vehicleDataEditText.trim());break;
			case 7: vehiclesMap.get(vin).setBodyType(vehicleDataEditText.trim());break;
			case 8: vehiclesMap.get(vin).setPrice(Float.parseFloat(vehicleDataEditText.trim()));break;
			case 9: vehiclesMap.get(vin).setPhotoUrl(new URL(vehicleDataEditText.trim()));break;
			default: System.out.println("Please input valid vehicleDataIndex");break;
		}
	}

	
	public void addVehicle(Vehicle vehicle) {
		vehiclesMap.put(vehicle.getId(), vehicle);

	}

	
	public void deleteVehicle(String vin) {
		vehiclesMap.remove(vin);	
		
	}
	
	public static ArrayList<Image> getVehicleImage(String bodyType) throws IOException{
		LinkedHashMap<String, ArrayList<Image>> vehicleImagesMap = getVehicleImagesMap();
		if(bodyType.equalsIgnoreCase("truck")) {
			return vehicleImagesMap.get("truck");
		}
		else if(bodyType.equalsIgnoreCase("suv")) {
			return vehicleImagesMap.get("suv");
		}
		else {
			return vehicleImagesMap.get("car");
		}
		
	}
	
	
	public static LinkedHashMap<String, ArrayList<Image>> getVehicleImagesMap() throws IOException {
		LinkedHashMap<String, ArrayList<Image>> vehicleImagesMap = new LinkedHashMap<String, ArrayList<Image>>();
		ArrayList<Image> truckImageList = new ArrayList<Image>();
		ArrayList<Image> suvImageList = new ArrayList<Image>();
		ArrayList<Image> carImageList = new ArrayList<Image>();
		File dir = new File("data/images/");
		File[] files=dir.listFiles();
		for(File file : files) {
			if(file.isFile() && file.getName().endsWith(".jpg") && file.getName().startsWith("truck")) {
				BufferedImage truckImage = ImageIO.read(file);
				truckImageList.add(truckImage);	
			}
			else if(file.isFile() && file.getName().endsWith(".jpg") && file.getName().startsWith("suv")) {
				BufferedImage suvImage = ImageIO.read(file);
				suvImageList.add(suvImage);	
			}
			else if(file.isFile() && file.getName().endsWith(".jpg") && file.getName().startsWith("car")) {
				BufferedImage carImage = ImageIO.read(file);
				carImageList.add(carImage);	
			}
			else {
				continue;
			}
		}
		vehicleImagesMap.put("truck", truckImageList);
		vehicleImagesMap.put("suv", suvImageList);
		vehicleImagesMap.put("car", carImageList);
		
		return vehicleImagesMap;
		
		
	}
	
	
	

}
