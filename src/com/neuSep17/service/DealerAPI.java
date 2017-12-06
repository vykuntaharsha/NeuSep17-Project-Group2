package com.neuSep17.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.neuSep17.dto.Dealer;

public class DealerAPI {
	
	private LinkedHashMap<String, Dealer> DealerMap;
	private String fileName;
	
	public ArrayList<Dealer> getDealers() {
		return new ArrayList<Dealer>(DealerMap.values());
	}
	
	public DealerAPI(String file) {
		this.fileName = file;
		try {
			DealerMap = getDealersMap(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected LinkedHashMap<String, Dealer> getDealersMap(String file) throws IOException {
		File inventoryFile = new File(file);
		BufferedReader reader = new BufferedReader(new FileReader(inventoryFile));
		LinkedHashMap<String, Dealer> dealerObjMap = new LinkedHashMap<String, Dealer>();		
		String line = null;
		
		while((line=reader.readLine())!=null) {
			String[] dealerArray = line.split("\t");
			//should be "Dealer dealer =new Dealer(dealerArray[0],dealerArray[1], dealerArray[2])"?
			Dealer dealer =new Dealer(dealerArray[0],dealerArray[1], dealerArray[2]);
			//should be dealerObjMap?
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
