package com.neuSep17.service;

import com.neuSep17.dao.DealerDAO;
import com.neuSep17.dto.Dealer;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DealerAPI {
	
	private DealerDAO dealerDAO;
	public DealerAPI(String filePath) {
		this.dealerDAO = new DealerDAO(filePath); 
		
	}
	

	
	public ArrayList<Dealer> getDealers() {
		return dealerDAO.getDealers();
	}

	
	public Dealer getDealer(String id) {
		return dealerDAO.getDealer(id);
		
	}

	
	public LinkedHashMap<String, Dealer> getDealerMap() {
		return dealerDAO.getDealerMap();
	}
	
}
