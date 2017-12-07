package com.neuSep17.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.neuSep17.dto.Incentive;
import com.neuSep17.dto.Vehicle;

public class IncentiveServiceAPI_Test {

	private LinkedHashMap<String, Incentive> incentivesMap;
	private String fileName;


	public IncentiveServiceAPI_Test(String file) {
		this.fileName = file;
		try {
			incentivesMap = getIncentivesMap(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	public ArrayList<Incentive> getIncentives() {
		return new ArrayList<Incentive>(incentivesMap.values());
	}
	
	public boolean isExpired(Incentive incentive) {
		if(LocalDate.now().isBefore(LocalDate.parse(incentive.getStartDate())) || LocalDate.now().isAfter(LocalDate.parse(incentive.getEndDate()))){
			return true;
		}
		else {
			return false;
		}
	}
	
	public float getAllDiscount(Vehicle vehicle) {
		float allDiscount = 0;
		ArrayList<Incentive> incentiveList = getAllIncentiveList(vehicle);
		for(Incentive incentive : incentiveList) {
			allDiscount+= incentive.getDiscount();
		}
		return allDiscount;
	}
	
	public ArrayList<Incentive> getAllIncentiveList(Vehicle vehicle) {
		//float allDiscount = 0;
		ArrayList<Incentive> incentiveList = new ArrayList<Incentive>();
		incentive:
		for(Incentive incentive : this.incentivesMap.values()) {
			if(!isExpired(incentive)) {
				ArrayList<String> criterion = incentive.getCriterion();
	
				if(criterion.get(0).equalsIgnoreCase("all")) {
					//allDiscount+=incentive.getDiscount();
					incentiveList.add(incentive);
					continue incentive;
				}
				else if(criterion.get(0).equalsIgnoreCase(vehicle.getId())) {
					//allDiscount+=incentive.getDiscount();
					incentiveList.add(incentive);
					continue incentive;
				}
				else if(criterion.get(0).equalsIgnoreCase("no")) {				
					criterion:
					for(int i=1; i<criterion.size();i++) {
						switch(i) {
							case 1: if(!criterion.get(i).equalsIgnoreCase("no") && !criterion.get(i).equalsIgnoreCase(vehicle.getCategory().toString())) {break criterion;}break;	
							case 2: if(!criterion.get(i).equalsIgnoreCase("no") && Integer.parseInt(criterion.get(i))!=vehicle.getYear()) {break criterion;}break;		
							case 3: if(!criterion.get(i).equalsIgnoreCase("no") && !criterion.get(i).equalsIgnoreCase(vehicle.getMake())) {break criterion;}break;		
							case 4: if(!criterion.get(i).equalsIgnoreCase("no") && !criterion.get(i).equalsIgnoreCase(vehicle.getModel())) {break criterion;}break;		
							case 5: if(!criterion.get(i).equalsIgnoreCase("no") && !criterion.get(i).equalsIgnoreCase(vehicle.getTrim())) {break criterion;}break;		
							case 6: if(!criterion.get(i).equalsIgnoreCase("no") && !criterion.get(i).equalsIgnoreCase(vehicle.getBodyType())) {break criterion;}break;		
							case 7: if(!criterion.get(i).equalsIgnoreCase("no") && Float.parseFloat(criterion.get(i))>vehicle.getPrice()) {break criterion;}break;
						}
						if (i==criterion.size()-1) {
							//allDiscount+=incentive.getDiscount();
							incentiveList.add(incentive);
						}
					}	
				}
				else {
					continue incentive;
				}
			
			}
			else {
				continue incentive;
			}
		}	
		return incentiveList;
	}
	
	
	
	

	public String getFileName() {
		return fileName;
	}


	public void saveIncentiveToFile() {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(new File(fileName)));
			writer.println("incentiveId~webId~title~discount~startDate~endDate~id,category,year,make,model,trim,type,price~description");
			for (Incentive incentive: incentivesMap.values()) {
				writer.println(incentive);
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	protected LinkedHashMap<String, Incentive> getIncentivesMap(String file) throws IOException {
		File incentiveFile = new File(file);
		BufferedReader reader = new BufferedReader(new FileReader(incentiveFile));
		LinkedHashMap<String, Incentive> incentives = new LinkedHashMap<String, Incentive>();		
		String line = null;
		int count = 0;
		
		while((line=reader.readLine())!=null) {
			count++;
			if(count==1) {continue;}
			String[] incentiveDataArray = line.split("~");
			Incentive incentive =new Incentive(incentiveDataArray);
			incentives.put(incentive.getId(), incentive);
		}
		reader.close();
		return incentives;
	
	}
	
	public int getTotalIncentiveAmount() {
		return incentivesMap.size();
	}
	
	
	
	public Incentive createIncentiveFromInput(String[] incentiveData) {
		Incentive incentive = new Incentive(incentiveData);
		return incentive;
	}
	
	
	public void editIncentive(String incentiveID, int incentiveDataIndex, String incentiveDataEditText) {
		switch(incentiveDataIndex) {
			case 2: incentivesMap.get(incentiveID).setTitle(incentiveDataEditText.trim());break;
			case 3: incentivesMap.get(incentiveID).setDiscount(Float.parseFloat(incentiveDataEditText.trim()));break;
			case 4: incentivesMap.get(incentiveID).setStartDate(incentiveDataEditText.trim());break;
			case 5: incentivesMap.get(incentiveID).setEndDate(incentiveDataEditText.trim());break;
			case 6: ArrayList<String> criterion = new ArrayList<String>();
					String[] criterionArr = incentiveDataEditText.split(",");
					for(String str : criterionArr) {
						criterion.add(str.trim());
					}
					incentivesMap.get(incentiveID).setCriterion(criterion);
					break;
			case 7: incentivesMap.get(incentiveID).setDescription(incentiveDataEditText.trim());break;
			default: System.out.println("Please input valid incentiveDataIndex");break;
		}
	}
	

	public void addIncentive(Incentive incentive) {
		incentivesMap.put(incentive.getId(), incentive);

	}

	
	public void deleteIncentive(String id) {
		incentivesMap.remove(id);	
		
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

	
//	public void displayIncentives() {
//		System.out.println(incentives.values());
//	}

}
