package com.neuSep17.service;

import com.neuSep17.dto.Dealer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

// use the relative path instead of url

public class DealerAPI {
	
	private LinkedHashMap<String, Dealer> DealerMap;
	private String filePath;

	public ArrayList<Dealer> getDealers() {
		return new ArrayList<Dealer>(DealerMap.values());
	}
	
	public DealerAPI(String filePath) {
		this.filePath = filePath;
		try {
			DealerMap = getDealersMap(filePath);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected LinkedHashMap<String, Dealer> getDealersMap(String fileUrl) throws IOException {
		File inventoryFile = new File(filePath);

//		URL url = new URL(fileUrl);
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		DataInputStream input = new DataInputStream(conn.getInputStream());

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

	public Dealer getPassword(String password){
		return DealerMap.get(password);
	}

	public LinkedHashMap<String, Dealer> getDealerMap() {
		return DealerMap;
	}
	
}
