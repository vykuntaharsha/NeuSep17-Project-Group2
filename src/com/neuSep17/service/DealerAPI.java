package com.neuSep17.service;

import com.neuSep17.dto.Dealer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

// use the URL instead of local absolute path

public class DealerAPI {
	
	private LinkedHashMap<String, Dealer> DealerMap;
	private String fileUrl;

	public ArrayList<Dealer> getDealers() {
		return new ArrayList<Dealer>(DealerMap.values());
	}
	
	public DealerAPI(String fileUrl) {
		this.fileUrl = fileUrl;
		try {
			DealerMap = getDealersMap(fileUrl);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected LinkedHashMap<String, Dealer> getDealersMap(String fileUrl) throws IOException {
//		File inventoryFile = new File(file);

		URL url = new URL(fileUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		DataInputStream input = new DataInputStream(conn.getInputStream());


//		BufferedReader reader = new BufferedReader(new FileReader(inventoryFile));
		LinkedHashMap<String, Dealer> dealerObjMap = new LinkedHashMap<String, Dealer>();		
		String line = null;
		
		while((line = input.readLine())!=null) {
			String[] dealerArray = line.split("\t");
			Dealer dealer = new Dealer(dealerArray[0],dealerArray[1], dealerArray[2]);
			dealerObjMap.put(dealer.getId(), dealer);
		}
//		reader.close();
		return dealerObjMap;
	}
	
	public Dealer getDealer(String id) {
		return DealerMap.get(id);
		
	}

	public LinkedHashMap<String, Dealer> getDealerMap() {
		return DealerMap;
	}
	
}
