package com.neuSep17.service;


import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.neuSep17.dao.IncentiveDAO;
import com.neuSep17.dto.Incentive;
import com.neuSep17.dto.Vehicle;

public class IncentiveServiceAPI_Test {
	
	private IncentiveDAO incentiveDAO;

	public IncentiveServiceAPI_Test(String file) {
		this.incentiveDAO = new IncentiveDAO(file);
	}
	

	
	public ArrayList<Incentive> getIncentives() {
		return incentiveDAO.getIncentives();
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
		ArrayList<Incentive> incentiveList = new ArrayList<Incentive>();
		incentive:
		for(Incentive incentive : incentiveDAO.getIncentivesMap().values()) {	
			if(!isExpired(incentive)) {
				ArrayList<String> criterion = incentive.getCriterion();
	
				if(criterion.get(0).equalsIgnoreCase("all")) {
					incentiveList.add(incentive);
					continue incentive;
				}
				else if(criterion.get(0).equalsIgnoreCase(vehicle.getId())) {
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
		return incentiveDAO.getFileName();
	}


	public void saveIncentiveToFile() {
		incentiveDAO.saveIncentiveToFile();
	}

	
	public int getTotalIncentiveAmount() {
		return incentiveDAO.getTotalIncentiveAmount();
	}
	
	
	public Incentive createIncentiveFromInput(String[] incentiveData) {
		Incentive incentive = new Incentive(incentiveData);
		return incentive;
	}
	
	
	public void addIncentive(Incentive incentive) {
		incentiveDAO.addIncentive(incentive);

	}

	
	public void deleteIncentive(String id) {
		incentiveDAO.deleteIncentive(id);
		
	}
	
	
	public void editIncentive(String incentiveID, int incentiveDataIndex, String incentiveDataEditText) {
		incentiveDAO.editIncentive(incentiveID, incentiveDataIndex, incentiveDataEditText);
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

	

}
