package com.neuSep17.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.neuSep17.dto.Dealer;

public class DealerDAO {
	
	private LinkedHashMap<String, Dealer> DealerMap;
	private String filePath;
	
	public DealerDAO(String filePath) {
		this.filePath = filePath;
		try {
			DealerMap = getDealersMap(filePath);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public ArrayList<Dealer> getDealers() {
		return new ArrayList<Dealer>(DealerMap.values());
	}

	protected LinkedHashMap<String, Dealer> getDealersMap(String filePath) throws IOException {
		File inventoryFile = new File(filePath);

		BufferedReader reader = new BufferedReader(new FileReader(inventoryFile));
		LinkedHashMap<String, Dealer> dealerObjMap = new LinkedHashMap<String, Dealer>();		
		String line = null;
		
		while((line = reader.readLine())!=null) {
			String[] dealerArray = line.split("\t");
			Dealer dealer = new Dealer(dealerArray[0],dealerArray[1], dealerArray[2], dealerArray[3]);
			dealerObjMap.put(dealer.getId(), dealer);
		}
		reader.close();
		return dealerObjMap;
	}
	
	public Dealer getDealer(String id) {
		return DealerMap.get(id);
		
	}

	public LinkedHashMap<String, Dealer> getDealerMap() {
		return DealerMap;
	}
	
	

}
