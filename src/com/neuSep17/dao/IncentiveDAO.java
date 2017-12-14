package com.neuSep17.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.neuSep17.dto.Incentive;

public class IncentiveDAO {
	
	private LinkedHashMap<String, Incentive> incentivesMap;
	private String fileName;

	public IncentiveDAO(String file) {
		this.fileName = file;
		try {
			incentivesMap = getIncentivesMap(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	public LinkedHashMap<String, Incentive> getIncentivesMap(){
		return incentivesMap;
	}

	
	public ArrayList<Incentive> getIncentives() {
		return new ArrayList<Incentive>(incentivesMap.values());
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

	
	public int getTotalIncentiveAmount() {
		return incentivesMap.size();
	}
	
	
	public void addIncentive(Incentive incentive) {
		incentivesMap.put(incentive.getId(), incentive);

	}

	
	public void deleteIncentive(String id) {
		incentivesMap.remove(id);	
		
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
	

	

}
